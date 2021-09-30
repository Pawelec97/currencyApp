package com.example.currencyapp.controllers;

import com.example.currencyapp.dto.CreateUserDto;
import com.example.currencyapp.dto.UserDto;
import com.example.currencyapp.mappers.UserMapper;
import com.example.currencyapp.model.User;
import com.example.currencyapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public void registerUser(@RequestBody CreateUserDto createUserDto) {
        userService.registerUser(createUserDto);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.findAll().stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserInfo(@PathVariable("id") long id) {
        User user = userService.findById(id);
        return new ResponseEntity<>(userMapper.mapToDto(user), HttpStatus.OK);
    }

}
