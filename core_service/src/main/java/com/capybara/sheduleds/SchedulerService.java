package com.capybara.sheduleds;

import com.capybara.dto.AnswerMessage;
import com.capybara.dto.CongratulationMessage;
import com.capybara.entities.BonusCard;
import com.capybara.entities.Client;
import com.capybara.rabbitmq.RabbitMQProducer;
import com.capybara.services.BonusCardService;
import com.capybara.services.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.capybara.constants.BirthdayMessageConstant.POINTS_FOR_BIRTHDAY_MESSAGE;

@Slf4j
@Component
public class SchedulerService {

    private static final String CRON = "0 0 10 * * *";
//    private static final String CRON = "*/10 * * * * *";
    private final ClientService clientService;
    private final BonusCardService bonusCardService;
    private final RabbitMQProducer rabbitMQProducer;

    public SchedulerService(ClientService clientService,
                            BonusCardService bonusCardService, RabbitMQProducer rabbitMQProducer) {
        this.clientService = clientService;
        this.bonusCardService = bonusCardService;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @Scheduled(cron = CRON)
    public void sendMailToUsers() {
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        int day = date.getDayOfMonth();
        List<Client> list = clientService.getUsersByBirthday(month, day);
        if (!list.isEmpty()) {
            list.forEach(client -> {
                try {
                    if(client.isActive()){
                        //send data to mail_service via rabbitmq
                        rabbitMQProducer.produceCongratulationBirthday(CongratulationMessage.builder()
                                        .email(client.getEmail())
                                        .username(client.getUsername())
                                .build());
//                        rabbitMQProducer.produceCongratulationBirthday(client.getEmail());
                        //TODO отправлять вместе с именем пользователя
//                        emailService.send(client.getEmail(), HAPPY_BIRTHDAY, client.getUsername());
                        BonusCard bonusCard = bonusCardService.getByChatId(client.getChatId());
                        BigDecimal amount = bonusCard.getAmount();
                        amount = amount.add(new BigDecimal("300"));
                        bonusCard.setAmount(amount);
                        bonusCardService.save(bonusCard);
                        log.info("Email have been sent. User id: {}, Date: {}", client.getId(), date);
                        //send a message about bonus accrual to tg_service
                        rabbitMQProducer.produceAnswerMessage(AnswerMessage.builder()
                                        .chatId(client.getChatId())
                                        .message(String.format("%s%s", POINTS_FOR_BIRTHDAY_MESSAGE, amount))
                                .build());
                    }
                } catch (Exception e) {
                    log.error("Email can't be sent.User's id: {}, Error: {}", client.getId(), e.getMessage());
                    log.error("Email can't be sent", e);
                }
            });
        }
    }

}
