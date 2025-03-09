package com.capybara.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {
    @Value("${queue.registration-mail}")
    private String registrationMailQueue;

    @Value("${queue.answer-message-queue}")
    private String answerMessageQueue;

    @Value("${queue.registration-state}")
    private String registrationStateQueue;

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue registrationMailQueue() {
        return new Queue(registrationMailQueue, true);
    }

    @Bean
    public Queue answerMessageQueue() {
        return new Queue(answerMessageQueue, true);
    }

    @Bean
    public Queue registrationStateQueue() {
        return new Queue(registrationStateQueue, true);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
