package com.capybara.coffee_house.dto;


import com.capybara.coffee_house.entities.Category;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private Long id;

    @NotEmpty(message = "The name is required")
    private String name;

    @Size(min=10, message = "The description should be at least 10 characters")
    @Size(max=1000, message = "The description cannot exceed 1000 characters")
    private String description;

    private Category category;
}
