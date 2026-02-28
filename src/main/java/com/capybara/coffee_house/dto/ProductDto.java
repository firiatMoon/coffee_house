package com.capybara.coffee_house.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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

    @Size(min=5, message = "The description should be at least 5 characters")
    @Size(max=1000, message = "The description cannot exceed 1000 characters")
    private String description;

    private CategoryDto category;
}
