package com.backend.dto;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class CommentDto
{
    private int commentId;
    @NotNull
    private int userId;
    @NotEmpty
    private String comment;
    @NotNull
    private int taskId;
    private Date timestamp;
    private String attachmentURL;
}