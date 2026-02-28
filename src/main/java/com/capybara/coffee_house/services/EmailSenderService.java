package com.capybara.coffee_house.services;


import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.TemplateEngine;

import java.util.Map;


@Service
public class EmailSenderService {

    public static final String USER_ACCOUNT_VERIFICATION = "User Account Verification";
    public static final String EMAIL_TEMPLATE = "congratulation/birthday";

    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromEmail;
    @Value("${service.activation.uri}")
    private String activationServiceUri;


    @Autowired
    public EmailSenderService(JavaMailSender emailSender, TemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    //verification email
    public void send(String to, String chatId) {
        try {
            MimeMessage mailMessage = emailSender.createMimeMessage();
            mailMessage.setFrom(new InternetAddress(fromEmail));
            mailMessage.setRecipients(Message.RecipientType.TO, to);
            mailMessage.setSubject(USER_ACCOUNT_VERIFICATION);
            mailMessage.setText(getActivationMailMessage(chatId));

            mailMessage.setContent(getActivationMailMessage(chatId), "text/html; charset=UTF-8");
            emailSender.send(mailMessage);
        } catch (MessagingException messageException) {
            throw new RuntimeException(messageException.getMessage());
        }
    }

    //congratulation birthday
    public void send(String to, String subject, String name) {
        try {
            Context context = new Context();
            context.setVariables(Map.of("name", name));
            String text = templateEngine.process(EMAIL_TEMPLATE, context);
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setSubject(subject);
            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setText(text, true);

            emailSender.send(message);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            throw new RuntimeException(exception.getMessage());
        }
    }

    private String getActivationMailMessage(String chatId){
        String message = String.format("Hello! Please click the " +
                "<a href=%s>link</a> below to verify your email.%n%nThe support Team.",activationServiceUri);
        return message.replace("{chatId}", chatId);
    }
}
