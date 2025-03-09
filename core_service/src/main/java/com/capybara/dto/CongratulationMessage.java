package com.capybara.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CongratulationMessage {

    private String email;
    private String username;
}
