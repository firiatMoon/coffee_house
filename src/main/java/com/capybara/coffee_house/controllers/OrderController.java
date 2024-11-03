package com.capybara.coffee_house.controllers;

import com.capybara.coffee_house.entities.User;
import com.capybara.coffee_house.services.MenuService;
import com.capybara.coffee_house.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final MenuService menuService;
    private final UserService userService;

    @Autowired
    public OrderController(MenuService menuService, UserService userService) {
        this.menuService = menuService;
        this.userService = userService;
    }

    @GetMapping({"", "/"})
    public String showOrderPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username);

        model.addAttribute("menuItems", menuService.findAll());
        model.addAttribute("user", user);
        return "order/form";
    }
}
