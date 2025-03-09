package com.capybara.services.handlers;

import com.capybara.dro.MailParams;
import com.capybara.entities.TelegramBotInformation;
import com.capybara.enums.RegistrationState;
import com.capybara.rabbitmq.RabbitMQProducer;
import com.capybara.repositories.TelegramRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.capybara.constants.TelegramBotConstant.ASK_VALIDITY_EMAIL;
import static com.capybara.constants.TelegramBotConstant.CONFIRMATION_OF_REGISTRATION;

@Slf4j
@Service
public class AskEmailRegistrationHandler implements TelegramMessageHandler{
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";


    private final TelegramRepository telegramRepository;
    private final RabbitMQProducer rabbitMQProducer;

    @Autowired
    public AskEmailRegistrationHandler(TelegramRepository telegramRepository, RabbitMQProducer rabbitMQProducer) {
        this.telegramRepository = telegramRepository;
        this.rabbitMQProducer = rabbitMQProducer;
    }

    @Override
    public SendMessage handle(String email, Long chatId, TelegramBotInformation telegramBotInformation) {
        if (isValidEmail(email)) {
            telegramBotInformation.setRegistrationState(RegistrationState.WAITING_FOR_REGISTRATION);
            telegramRepository.save(telegramBotInformation);
            //send email data via rabbitMQ to core_service
            sendRegistrationMail(chatId, email);

            return SendMessage.builder()
                    .chatId(chatId)
                    .text(CONFIRMATION_OF_REGISTRATION)
                    .build();
        } else {
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(ASK_VALIDITY_EMAIL)
                    .build();
        }
    }

    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void sendRegistrationMail(Long chatId, String email) {
        MailParams mailParams = MailParams.builder()
                .chatId(chatId)
                .email(email)
                .build();
        log.info("Sending registration mail to {}", email);
        rabbitMQProducer.produceRegistrationMail(mailParams);
    }
}
