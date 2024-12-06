package com.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.dto.UserDto;
import com.backend.entity.User;
import com.backend.exception.ResourceNotFoundException;

public interface UserService {
    UserDto createUser(UserDto user);
    
    UserDto updateUser(UserDto user, Integer userId) throws ResourceNotFoundException;

    UserDto getUserById(Integer userId);

    List<UserDto> getAllUsers();

    void deleteUser(Integer userId);
}
