package com.capybara.coffee_house.services;

import com.capybara.coffee_house.dto.UserFormDto;
import com.capybara.coffee_house.entities.User;
import org.springframework.stereotype.Service;

@Service
public class UserFormService {

    //Конвертацию из User в UserDto
    public UserFormDto convertToDto(User user) {
        return UserFormDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password("")
                .confirmPassword("")
                .email(user.getEmail())
                .phone(user.getPhone())
                .birthday(user.getBirthday())
                .role(user.getRole())
                .build();
    }

    //Конвертацию из UserDto в User
    public User convertFromDto(UserFormDto userFormDto) {
        return User.builder()
                .id(userFormDto.getId())
                .username(userFormDto.getUsername())
                .password(userFormDto.getPassword())
                .email(userFormDto.getEmail())
                .phone(userFormDto.getPhone())
                .birthday(userFormDto.getBirthday())
                .role(userFormDto.getRole())
                .build();
    }
}
