package com.capybara.coffee_house.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UnexpectedErrorResponse {
    private String message;
}