package com.capybara.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "service.pass")
public class PassDataProperties {
    private String charIdUri;
    private String usernameUri;
    private String bonusCardUri;
    private String isActiveUri;
    private String birthdayUri;
    private String phoneUri;
    private String emailUri;
}
