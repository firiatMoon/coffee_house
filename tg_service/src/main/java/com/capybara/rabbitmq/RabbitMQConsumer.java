package com.capybara.rabbitmq;


import com.capybara.dro.AnswerMessage;
import com.capybara.services.MessageSender;
import com.capybara.services.TelegramService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Slf4j
@Component
@EnableRabbit
public class RabbitMQConsumer {

    private final MessageSender messageSender;
    private final TelegramService telegramService;

    @Autowired
    public RabbitMQConsumer(MessageSender messageSender, TelegramService telegramService) {
        this.messageSender = messageSender;
        this.telegramService = telegramService;
    }

    @RabbitListener(queues = "${queue.answer-message-queue}")
    public void consumeAnswerMessage(AnswerMessage answerMessage) {
        log.info("Received from rabbit - answer message {}: {}", answerMessage.getMessage(), answerMessage.getChatId());
        SendMessage message = SendMessage.builder()
                .chatId(answerMessage.getChatId())
                .text(answerMessage.getMessage())
                .build();
        messageSender.sendMessage(message);
    }

    @RabbitListener(queues = "${queue.registration-state}")
    public void consumeRegistrationState(Long chatId) {
        log.info("Received from rabbit - registration state {}:", chatId);
        telegramService.saveRegistrationState(chatId);
    }
}
