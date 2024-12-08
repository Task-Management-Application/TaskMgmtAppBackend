package com.backend.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Comment {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private int commentId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String comment;
    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
    private Date timestamp;
    private String attachmentURL;
}
