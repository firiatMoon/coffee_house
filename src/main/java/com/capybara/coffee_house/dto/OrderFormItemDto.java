package com.capybara.coffee_house.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderFormItemDto {
    private Long menuId;
    private BigDecimal quantity;
}
