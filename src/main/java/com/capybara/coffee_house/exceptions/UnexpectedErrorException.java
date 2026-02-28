package com.capybara.coffee_house.exceptions;

public class UnexpectedErrorException extends RuntimeException{
    public UnexpectedErrorException(String message){
        super(message);
    }
}
