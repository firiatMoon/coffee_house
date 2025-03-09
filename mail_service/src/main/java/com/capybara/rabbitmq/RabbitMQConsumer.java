package com.capybara.rabbitmq;

import com.capybara.dto.CongratulationMessage;
import com.capybara.dto.MailParams;
import com.capybara.services.EmailSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMQConsumer {

    private final EmailSenderService emailSenderService;

    @Autowired
    public RabbitMQConsumer(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @RabbitListener(queues = "${queue.congratulation-birthday}")
    public void consumeCongratulationBirthday(CongratulationMessage congratulationMessage) {
        log.info("Received from rabbit - congratulation birthday {}", congratulationMessage.getEmail());
        emailSenderService.sendCongratulationBirthday(congratulationMessage.getEmail(),
                congratulationMessage.getUsername());
    }

    @RabbitListener(queues = "${queue.verification-mail}")
    public void consumeVerificationMail(MailParams mailParams){
        log.info("Received from rabbit - verification mail {}", mailParams.getEmail());
        emailSenderService.sendVerificationEmail(mailParams.getEmail(), mailParams.getChatId());
    }
}
