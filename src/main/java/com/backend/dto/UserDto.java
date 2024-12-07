package com.backend.dto;

import java.util.Date;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class UserDto
{
    private Integer userId;

	@NotNull
	@NotEmpty(message = "Name must not be empty")
    private String name;
	
	@Email
	private String emailId;
	
	@NotNull(message = "Password must not be null")
	@NotEmpty
	@Size(min = 3, max = 10, message = "Password must be of min 3 and max 10 characters")
	private String password;

	@NotEmpty(message = "Dob must not be empty")
    private String dob;
	private Date createdAt;
	private Date lastLoginAt;
	private String profilePic;
}