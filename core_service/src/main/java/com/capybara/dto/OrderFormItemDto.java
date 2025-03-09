package com.capybara.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderFormItemDto {
    private Long menuItemId;
    private BigDecimal quantity;
}
