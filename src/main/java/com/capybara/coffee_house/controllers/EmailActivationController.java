package com.capybara.coffee_house.controllers;

import com.capybara.coffee_house.services.ClientActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/mail")
public class EmailActivationController {

    private final ClientActivationService clientActivationService;

    @Autowired
    public EmailActivationController(ClientActivationService clientActivationService) {
        this.clientActivationService = clientActivationService;
    }

    @GetMapping("/activation")
    public String activation(@RequestParam("chatId") String chatId, Model model) {
        boolean result = clientActivationService.activation(chatId);
        model.addAttribute("result", result);
        return "auth/verification_email";
    }
}
