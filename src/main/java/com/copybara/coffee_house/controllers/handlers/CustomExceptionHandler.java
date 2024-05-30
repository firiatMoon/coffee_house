package com.copybara.coffee_house.controllers.handlers;

import com.copybara.coffee_house.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;



@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleEntityNotFound(EntityNotFoundException exception){
        ModelAndView modelAndView = new ModelAndView("/error/not_found");
        modelAndView.addObject("message", exception.getMessage());
        return modelAndView;
    }

    @ExceptionHandler({Exception.class})
    public ModelAndView handleException(Exception exception){
        ModelAndView modelAndView = new ModelAndView("/error/generic_error");
        modelAndView.addObject("exception", exception.getStackTrace());
        return modelAndView;
    }
}
