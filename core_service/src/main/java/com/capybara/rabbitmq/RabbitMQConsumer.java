package com.capybara.rabbitmq;

import com.capybara.dto.MailParams;
import com.capybara.services.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitMQConsumer {

   private final ClientService clientService;

   @Autowired
    public RabbitMQConsumer(ClientService clientService) {
       this.clientService = clientService;
   }

    @RabbitListener(queues = "${queue.registration-mail}")
    public void consumeRegistrationMail(MailParams mailParams) {
        log.info("Received from rabbit - registration mail {}", mailParams.getEmail());
        clientService.registrationMail(mailParams);
    }
}
