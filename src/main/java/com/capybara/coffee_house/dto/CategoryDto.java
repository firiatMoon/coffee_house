package com.capybara.coffee_house.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDto {

    private Integer id;

    @NotEmpty(message = "The title is required")
    private String title;
}
