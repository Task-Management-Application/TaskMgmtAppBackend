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
public class Comment {
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private int commentId;
    private int userId;
    private String comment;
    private int taskId;
    private Date timestamp;
    private String attachmentURL;
}
