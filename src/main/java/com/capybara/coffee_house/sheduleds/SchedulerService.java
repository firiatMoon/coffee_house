package com.capybara.coffee_house.sheduleds;

import com.capybara.coffee_house.entities.BonusCard;
import com.capybara.coffee_house.entities.Client;
import com.capybara.coffee_house.services.BonusCardService;
import com.capybara.coffee_house.services.ClientService;
import com.capybara.coffee_house.services.EmailSenderService;
import com.capybara.coffee_house.services.telegrambot.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.capybara.coffee_house.constants.BirthdayMessageConstant.HAPPY_BIRTHDAY;
import static com.capybara.coffee_house.constants.BirthdayMessageConstant.POINTS_FOR_BIRTHDAY_MESSAGE;

@Slf4j
@Component
public class SchedulerService {

    private static final String CRON = "0 0 10 * * *";
//    private static final String CRON = "*/10 * * * * *";
    private final ClientService clientService;
    private final EmailSenderService emailService;
    private final BonusCardService bonusCardService;
    private final MessageSender messageSender;

    public SchedulerService(ClientService clientService, EmailSenderService emailService, BonusCardService bonusCardService, MessageSender messageSender) {
        this.clientService = clientService;
        this.emailService = emailService;
        this.bonusCardService = bonusCardService;
        this.messageSender = messageSender;
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
                        emailService.send(client.getEmail(), HAPPY_BIRTHDAY, client.getUsername());
                        BonusCard bonusCard = bonusCardService.getByChatId(client.getChatId());
                        BigDecimal amount = bonusCard.getAmount();
                        amount = amount.add(new BigDecimal("300"));
                        bonusCard.setAmount(amount);
                        bonusCardService.save(bonusCard);
                        log.info("Email have been sent. User id: {}, Date: {}", client.getId(), date);
                        pointsForBirthdayMessage(client.getChatId(), amount);
                    }
                } catch (Exception e) {
                    log.error("Email can't be sent.User's id: {}, Error: {}", client.getId(), e.getMessage());
                    log.error("Email can't be sent", e);
                }
            });
        }
    }

    private void pointsForBirthdayMessage(Long clientId, BigDecimal amount) {
        SendMessage message = SendMessage.builder()
                .chatId(clientId)
                .text(String.format("%s%s", POINTS_FOR_BIRTHDAY_MESSAGE, amount))
                .build();
        messageSender.sendMessage(message);
    }

}
