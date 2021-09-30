package com.example.currencyapp.services;

import com.example.currencyapp.dto.CreateUserDto;
import com.example.currencyapp.exceptions.UserNotFoundException;
import com.example.currencyapp.model.User;
import com.example.currencyapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void registerUser(CreateUserDto createUserDto) {
        User user = new User();
        user.setFirstName(createUserDto.getFirstName());
        user.setLastName(createUserDto.getLastName());
        user.setPlnBalance(createUserDto.getStartBalance());
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

}
