package com.capybara.coffee_house.dto;

import com.capybara.coffee_house.entities.Product;
import com.capybara.coffee_house.entities.Unit;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuDto {

    private Long id;

    private Product product;

    private Unit unit;

    @Min(0)
    private BigDecimal quantity;

    @Min(0)
    private BigDecimal price;
}
