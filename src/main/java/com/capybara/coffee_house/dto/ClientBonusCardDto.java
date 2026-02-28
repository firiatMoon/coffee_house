package com.capybara.coffee_house.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientBonusCardDto {
    private Long id;
    private ClientDto client;
    private BigDecimal amount;
    private int discountPercent;
}
