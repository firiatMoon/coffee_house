package com.copybara.coffee_house.dto;


import jakarta.validation.constraints.*;
import lombok.*;

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
}
