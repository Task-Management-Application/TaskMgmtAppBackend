package com.backend.converter;

import java.beans.JavaBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.backend.dto.UserDto;
import com.backend.entity.User;
import com.backend.exception.ResourceNotFoundException;
@Component
public class UserConverter {

    public User UserDtoToUser(UserDto userDto)
    {
        User newUser = new User();
        newUser.setCreatedAt(userDto.getCreatedAt());
        newUser.setDob(userDto.getDob());
        newUser.setEmailId(userDto.getEmailId());
        newUser.setLastLoginAt(userDto.getLastLoginAt());
        newUser.setName(userDto.getName());
        newUser.setPassword(userDto.getPassword());
        newUser.setProfilePic(userDto.getProfilePic());
        return newUser;
    }

    public UserDto UserToUserDto(User user)
    {
        UserDto newDto = new UserDto();
        newDto.setCreatedAt(user.getCreatedAt());
        newDto.setDob(user.getDob());
        newDto.setEmailId(user.getEmailId());
        newDto.setLastLoginAt(user.getLastLoginAt());
        newDto.setName(user.getName());
        newDto.setPassword(user.getPassword());
        newDto.setProfilePic(user.getProfilePic());
        newDto.setUserId(user.getUserId());
        return newDto;
    }
}
