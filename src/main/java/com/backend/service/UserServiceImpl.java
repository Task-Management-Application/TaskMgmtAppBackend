package com.backend.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.dto.UserDto;
import com.backend.entity.User;
import com.backend.exception.ResourceNotFoundException;
import com.backend.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDto createUser(UserDto user)
    {
        if(user.getCreatedAt()==null)
            user.setCreatedAt(new Date());
        User newUser = dtoToUser(user);
        this.userRepo.save(newUser);
        return userToDto(newUser);
    }

    @Override
    public UserDto updateUser(UserDto user, Integer userId) throws ResourceNotFoundException
    {
        User foundUser = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
        if(user.getName()!=null)        
            foundUser.setName(user.getName());
        if(user.getCreatedAt()!=null)
            foundUser.setCreatedAt(user.getCreatedAt());
        if(user.getDob()!=null)
            foundUser.setDob(user.getDob());
        if(user.getEmailId()!=null)
            foundUser.setEmailId(user.getEmailId());
        if(user.getLastLoginAt()!=null)
            foundUser.setLastLoginAt(user.getLastLoginAt());
        if(user.getPassword()!=null)
            foundUser.setPassword(user.getPassword());
        if(user.getProfilePic()!=null)
            foundUser.setProfilePic(user.getProfilePic());
        
        User updatedUser = this.userRepo.save(foundUser);
        return userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) throws ResourceNotFoundException
    {
        User foundUser = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
        return userToDto(foundUser);
    }

    @Override
    public List<UserDto> getAllUsers()
    {
        List<User> users = this.userRepo.findAll();
        return users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer userId)
    {
        User foundUser = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
        this.userRepo.delete(foundUser);
    }

    private User dtoToUser(UserDto userDto) {
        return this.modelMapper.map(userDto, User.class);
    }

    private UserDto userToDto(User user) {
        return this.modelMapper.map(user,UserDto.class);
    }
}