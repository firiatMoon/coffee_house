package com.capybara.coffee_house.controllers.api;

import com.capybara.coffee_house.dto.OrderFormDto;
import com.capybara.coffee_house.services.OrderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderRestController {
    private final OrderService orderService;

    @Autowired
    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<?> saveOrder(@RequestBody @Valid OrderFormDto orderFormDto) {
        orderService.saveOrder(orderFormDto);
        return ResponseEntity.ok().build();
    }
}
