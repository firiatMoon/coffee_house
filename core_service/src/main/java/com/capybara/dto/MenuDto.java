package com.capybara.dto;

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

    private ProductDto product;

    private UnitDto unit;

    @Min(0)
    private BigDecimal quantity;

    @Min(0)
    private BigDecimal price;
}
