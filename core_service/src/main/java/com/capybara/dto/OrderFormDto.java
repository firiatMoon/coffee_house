package com.capybara.dto;

import com.capybara.enums.BonusPointsAction;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderFormDto {
    private UserDto user;
    private ClientDto client;
    private BonusPointsAction bonusPointsAction;
    private List<OrderFormItemDto> items;
    private BigDecimal totalAmount;
    private BigDecimal discountAmount;
    private BigDecimal finalAmount;
}
