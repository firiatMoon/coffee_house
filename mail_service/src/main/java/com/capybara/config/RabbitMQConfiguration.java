package com.capybara.config;


import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

@Slf4j
@Configuration
public class RabbitMQConfiguration {
    @Value("${queue.congratulation-birthday}")
    private String congratulationBirthdayQueue;

    @Value("${queue.verification-mail}")
    private String verificationMailQueue;

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue congratulationBirthdayQueue() {
        return new Queue(congratulationBirthdayQueue, true);
    }

    @Bean
    public Queue verificationMailQueue() {
        return new Queue(verificationMailQueue, true);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

}
