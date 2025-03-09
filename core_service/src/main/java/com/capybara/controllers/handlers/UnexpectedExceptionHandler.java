package com.capybara.controllers.handlers;

import com.capybara.exceptions.UnexpectedErrorException;
import com.capybara.exceptions.UnexpectedErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UnexpectedExceptionHandler {
    private final static String ANSWER_MESSAGE = "An unexpected error occurred. Please try again later.";

    @ExceptionHandler({UnexpectedErrorException.class})
    public ResponseEntity<UnexpectedErrorResponse> handleUnexpectedError(UnexpectedErrorException exception){
        UnexpectedErrorResponse response = new UnexpectedErrorResponse(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
