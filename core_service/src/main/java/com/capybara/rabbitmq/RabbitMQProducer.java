package com.capybara.rabbitmq;

import com.capybara.dto.AnswerMessage;
import com.capybara.dto.CongratulationMessage;
import com.capybara.dto.MailParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${queue.congratulation-birthday}")
    private String congratulationBirthdayQueue;

    @Value("${queue.registration-state}")
    private String registrationStateQueue;

    @Value("${queue.verification-mail}")
    private String verificationMailQueue;

    @Value("${queue.answer-message-queue}")
    private String answerMessageQueue;

    @Autowired
    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void produceCongratulationBirthday(CongratulationMessage congratulationMessage) {
        log.info("Message sent to congratulation birthday queue: {}", congratulationMessage.getEmail());
        rabbitTemplate.convertAndSend(congratulationBirthdayQueue, congratulationMessage);
    }

    public void produceVerificationMail(MailParams mailParams) {
        rabbitTemplate.convertAndSend(verificationMailQueue, mailParams);
    }

    public void produceAnswerMessage(AnswerMessage answerMessage) {
        rabbitTemplate.convertAndSend(answerMessageQueue, answerMessage);
    }

    public void produceRegistrationState(Long registrationState) {
        rabbitTemplate.convertAndSend(registrationStateQueue, registrationState);
    }
}
