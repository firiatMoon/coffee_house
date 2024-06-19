package com.copybara.coffee_house.services;

import com.copybara.coffee_house.dto.UserDto;
import com.copybara.coffee_house.dto.UserFormDto;
import com.copybara.coffee_house.entities.User;
import org.springframework.stereotype.Service;

@Service
public class UserFormService {

    //Конвертацию из User в UserDto
    public UserFormDto convertToDto(User user) {
        UserFormDto.UserFormDtoBuilder builderDto = UserFormDto.builder();
        return builderDto
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
        User.UserBuilder builder = User.builder();
        return builder
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
