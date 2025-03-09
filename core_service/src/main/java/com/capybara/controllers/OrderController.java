package com.capybara.controllers;

import com.capybara.entities.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import com.capybara.services.MenuService;
import com.capybara.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
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
