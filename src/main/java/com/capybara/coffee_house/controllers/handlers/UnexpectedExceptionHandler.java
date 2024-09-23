package com.capybara.coffee_house.controllers.handlers;

import com.capybara.coffee_house.exceptions.UnexpectedErrorException;
import com.capybara.coffee_house.exceptions.UnexpectedErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UnexpectedExceptionHandler {
    private final static String ANSWER_MESSAGE = "An unexpected error occurred. Please try again later.";

    @ExceptionHandler({UnexpectedErrorException.class})
    public ResponseEntity<UnexpectedErrorResponse> handleUnexpectedError(UnexpectedErrorException exception){
        UnexpectedErrorResponse response = new UnexpectedErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
