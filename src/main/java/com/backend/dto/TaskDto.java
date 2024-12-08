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
import lombok.Builder.Default;

@NoArgsConstructor
@Getter
@Setter
public class TaskDto
{
    private int taskId;
    @NotEmpty
    @NotNull
    private String title;
    private String descrString;
    private String status;
    private int createdBy;
    private int assignedTo;
    private Date createdAt;
    private Date lastUpdatedAt;
    private int deadline;
    private int priority;
}