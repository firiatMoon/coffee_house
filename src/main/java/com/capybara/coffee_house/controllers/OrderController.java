package com.capybara.coffee_house.controllers;

import com.capybara.coffee_house.entities.Menu;
import com.capybara.coffee_house.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.capybara.coffee_house.dto.OrderFormDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/order")
public class OrderController {
    private final MenuService menuService;

    @Autowired
    public OrderController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping({"", "/"})
    public String showOrderPage(Model model) {
        OrderFormDto orderFormDto = new OrderFormDto();
        model.addAttribute("orderFormDto", orderFormDto);
        model.addAttribute("menuItems", menuService.findAll());
        return "order/form";
    }
}
