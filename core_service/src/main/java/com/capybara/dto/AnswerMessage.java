package com.capybara.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AnswerMessage {

    private Long chatId;
    private String message;
}
