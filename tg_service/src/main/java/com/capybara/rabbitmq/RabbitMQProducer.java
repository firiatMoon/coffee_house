package com.capybara.rabbitmq;

import com.capybara.dro.MailParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${queue.registration-mail}")
    private String registrationMailQueue;


    @Autowired
    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void produceRegistrationMail(MailParams mailParams) {
        rabbitTemplate.convertAndSend(registrationMailQueue, mailParams);
    }

}
