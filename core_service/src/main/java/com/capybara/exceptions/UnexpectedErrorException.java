package com.capybara.exceptions;

public class UnexpectedErrorException extends RuntimeException{
    public UnexpectedErrorException(String message){
        super(message);
    }
}
