package com.capybara.dro;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class MailParams {

    private Long chatId;
    private String email;
}
