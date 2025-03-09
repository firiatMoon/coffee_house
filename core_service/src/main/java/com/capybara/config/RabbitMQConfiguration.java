package com.capybara.config;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;



@Configuration
public class RabbitMQConfiguration {

    @Value("${queue.congratulation-birthday}")
    private String congratulationBirthdayQueue;

    @Value("${queue.registration-mail}")
    private String registrationMailQueue;

    @Value("${queue.verification-mail}")
    private String verificationMailQueue;

    @Value("${queue.answer-message-queue}")
    private String answerMessageQueue;

    @Value("${queue.registration-state}")
    private String registrationStateQueue;


    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue congratulationBirthdayQueue() {
        return new Queue(congratulationBirthdayQueue, true);
    }

    @Bean
    public Queue registrationMailQueue() {
        return new Queue(registrationMailQueue, true);
    }

    @Bean
    public Queue verificationMailQueue() {
        return new Queue(verificationMailQueue, true);
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
