package com.capybara.coffee_house.services;

import com.capybara.coffee_house.repositories.UserRepository;
import com.capybara.coffee_house.dto.UserDto;
import com.capybara.coffee_house.entities.User;
import com.capybara.coffee_house.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByPhone(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public void save(UserDto userDto) {
        if(userRepository.findByPhone(userDto.getPhone()).isPresent()){
            throw new EntityNotFoundException("User already exists");
        }
        if(!userDto.getPassword().equals(userDto.getConfirmPassword())){
            throw new BadCredentialsException("Passwords do not match");
        }
        User user = convertFromDto(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setActive(true);
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException(String.format("User with id = %s not found", id)
        ));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with name = %s not found", username)));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    //Конвертацию из User в UserDto
    public UserDto convertToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .phone(user.getPhone())
                .birthday(user.getBirthday())
                .role(user.getRole())
                .build();
    }

    //Конвертацию из UserDto в User
    public User convertFromDto(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .birthday(userDto.getBirthday())
                .role(userDto.getRole())
                .build();
    }
}
