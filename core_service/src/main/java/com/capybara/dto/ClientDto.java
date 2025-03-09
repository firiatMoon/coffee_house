package com.capybara.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {
    private Long id;
    private String username;
    private LocalDate birthday;
    private String phone;
    private String email;
}
