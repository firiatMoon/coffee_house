package com.capybara.coffee_house.controllers.api;

import com.capybara.coffee_house.dto.OrderFormDto;
import com.capybara.coffee_house.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> saveOrder(@RequestParam @Valid OrderFormDto orderFormDto) {
        orderService.saveOrder(orderFormDto);
        return ResponseEntity.ok().build();
    }
}
