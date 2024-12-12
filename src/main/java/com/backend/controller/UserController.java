package com.backend.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.dto.CommentDto;
import com.backend.dto.OrganisationDto;
import com.backend.dto.TaskDto;
import com.backend.dto.UserDto;
import com.backend.response.ApiResponse;
import com.backend.service.OrganisationService;
import com.backend.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    OrganisationService organisationService;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
        UserDto createdUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createdUserDto,HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateuser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId)
    {
        UserDto updatedUser = this.userService.updateUser(userDto, userId);
        return new ResponseEntity<>(updatedUser,HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer userId)
    {
        this.userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse("Successfully deleted the entry"),HttpStatus.OK);
    }
    
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers()
    {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @GetMapping("/{userId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsWithUserId(@PathVariable Integer userId)
    {
        return new ResponseEntity<>(this.userService.getAllCommentsByUserId(userId),HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserWithId(@PathVariable Integer userId)
    {
        return new ResponseEntity<>(this.userService.getUserById(userId),HttpStatus.OK);
    }

    @GetMapping("/{userId}/assignedTasks")
    public ResponseEntity<List<TaskDto>> getAssignedTasksForUserId(@PathVariable Integer userId)
    {
        return new ResponseEntity<>(this.userService.getAllAssignedTasksForUserId(userId),HttpStatus.OK);
    }

    @GetMapping("/{userId}/createdTasks")
    public ResponseEntity<List<TaskDto>> getCreatedTasksForUserId(@PathVariable Integer userId)
    {
        return new ResponseEntity<>(this.userService.getAllCreatedTasksForuserId(userId),HttpStatus.OK);
    }

    @GetMapping("/{userId}/organisations")
    public ResponseEntity<List<OrganisationDto>> getOrganisationsForUserId(@PathVariable Integer userId)
    {
        return new ResponseEntity<>(this.userService.getAllOrganisationsForuserId(userId),HttpStatus.OK);
    }

    @PostMapping("/join/{userId}/{organisationId}")
    public ResponseEntity<List<UserDto>> addUserToOrganisation(
            @PathVariable Integer requestingUserId,
            @PathVariable Integer requestedUserId,
            @PathVariable Integer organisationId) {
        return new ResponseEntity<>(this.userService.addUserToOrganisation(requestingUserId, requestedUserId, organisationId), HttpStatus.OK);
    }

    @PostMapping("/Admins/make/{requestingUserId}/{requestedUserId}/{organisationId}")
    public ResponseEntity makeAdmin(
            @PathVariable Integer requestingUserId,
            @PathVariable Integer requestedUserId,
            @PathVariable Integer organisationId) {
                this.organisationService.updateAdmin(requestingUserId, requestedUserId, true, organisationId);
        return new ResponseEntity(HttpStatus.OK);
    }

    // profile picture update
    @PostMapping("/{userId}/profile-pic")
    public ResponseEntity<String> uploadProfilePic(
            @PathVariable Integer userId,
            @RequestParam("file") MultipartFile file) {
        try {
            String fileName = userService.uploadProfilePic(userId, file);
            return ResponseEntity.ok("Profile picture uploaded successfully "+fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload profile picture");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{userId}/profile-pic")
    public ResponseEntity<String> downloadProfilePic(
            @PathVariable Integer userId,
            HttpServletResponse response
    ) throws IOException {

        try{
            InputStream resource = userService.downloadprofilePic(userId);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(resource,response.getOutputStream());
            return new ResponseEntity<>("Showing the profile pic",HttpStatus.FOUND);
        }
        catch(FileNotFoundException e)
        {
            return new ResponseEntity<>("File not found for the user profile pic",HttpStatus.NOT_FOUND);
        }
        
    }

}
