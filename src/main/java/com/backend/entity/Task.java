package com.backend.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Task {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private int taskId;
    private String title;
    private String descrString;
    private String status;
    private int createdBy;
    private int assignedTo;
    private Date createdAt;
    private Date lastUpdatedAt;
    private int deadline;
    private int priority;
    //private List<Comment> comments;
}
