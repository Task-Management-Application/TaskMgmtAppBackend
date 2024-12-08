package com.backend.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.converter.CommentConverter;
import com.backend.converter.TaskConverter;
import com.backend.converter.UserConverter;
import com.backend.dto.CommentDto;
import com.backend.dto.TaskDto;
import com.backend.dto.UserDto;
import com.backend.entity.Comment;
import com.backend.entity.Task;
import com.backend.entity.User;
import com.backend.exception.ResourceNotFoundException;
import com.backend.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private CommentConverter commentConverter;

    @Autowired
    private TaskConverter taskConverter;
    @Override
    public UserDto createUser(UserDto user)
    {
        if(user.getCreatedAt()==null)
            user.setCreatedAt(new Date());
        User newUser = this.userConverter.UserDtoToUser(user);
        this.userRepo.save(newUser);
        return this.userConverter.UserToUserDto(newUser);
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
        return this.userConverter.UserToUserDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) throws ResourceNotFoundException
    {
        User foundUser = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
        return this.userConverter.UserToUserDto(foundUser);
    }

    @Override
    public List<UserDto> getAllUsers()
    {
        List<User> users = this.userRepo.findAll();
        return users.stream().map(user->this.userConverter.UserToUserDto(user)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer userId)
    {
        User foundUser = this.userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
        this.userRepo.delete(foundUser);
    }

    @Override
    public List<CommentDto> getAllCommentsByUserId(Integer userId)
    {
        User foundUser = this.userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
        List<Comment> commentList = foundUser.getComments();
        return commentList.stream().map(comment -> commentConverter.commentToCommentDto(comment)).collect(Collectors.toList());
    } 

    @Override
    public List<TaskDto> getAllAssignedTasksForUserId(Integer userId)
    {
        User foundUser = this.userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
        List<Task> taskList = foundUser.getAssignedToTasks();
        return taskList.stream().map(task -> taskConverter.TaskToTaskDto(task)).collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getAllCreatedTasksForuserId(Integer userId)
    {
        User foundUser = this.userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
        List<Task> taskList = foundUser.getCreatedByTasks();
        return taskList.stream().map(task -> taskConverter.TaskToTaskDto(task)).collect(Collectors.toList());
    }
}
