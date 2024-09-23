package com.capybara.coffee_house.dto;

import com.capybara.coffee_house.enums.RegistrationState;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDto {
    private Long id;
    private String username;
    private LocalDate birthday;
    private String phone;
    private String email;
    private Long chatId;
    private RegistrationState registrationState;
}
