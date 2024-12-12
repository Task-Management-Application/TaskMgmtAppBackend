package com.backend.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.dto.CommentDto;
import com.backend.dto.OrganisationDto;
import com.backend.dto.TaskDto;
import com.backend.dto.UserDto;
import com.backend.entity.Organisation;
import com.backend.entity.User;
import com.backend.exception.ResourceNotFoundException;

public interface UserService {
    UserDto createUser(UserDto user);
    
    UserDto updateUser(UserDto user, Integer userId) throws ResourceNotFoundException;

    UserDto getUserById(Integer userId);

    List<UserDto> getAllUsers();

    void deleteUser(Integer userId);

    List<CommentDto> getAllCommentsByUserId(Integer userId);

    List<TaskDto> getAllAssignedTasksForUserId(Integer userId);

    List<TaskDto> getAllCreatedTasksForuserId(Integer userId);

    List<UserDto> addUserToOrganisation(Integer requestingUserId, Integer requestedUserId, Integer orgId);

    List<OrganisationDto> getAllOrganisationsForuserId(Integer userId);

    InputStream downloadprofilePic(Integer userId) throws FileNotFoundException;

    String uploadProfilePic(Integer userId, MultipartFile file) throws IOException;
}
