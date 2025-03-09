package com.capybara.dto;

import com.capybara.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserDto {
    private Long id;

    @NotEmpty(message = "Username should not be empty")
    private String username;

    @NotEmpty(message = "Password should not be empty")
    private String password;

    @NotEmpty(message = "Password should not be empty")
    private String confirmPassword;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email should not be empty")
    private String email;

    @NotEmpty(message = "Phone number should not be empty")
    @Pattern(regexp = "^([0-9]{10})$", message = "Phone number is not correct")
    private String phone;

    private Role role;

    private LocalDate birthday;
}
