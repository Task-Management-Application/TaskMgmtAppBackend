package com.backend.entity;

import java.util.Date;
import java.util.List;

public class Task {
    private int taskId;
    private String title;
    private String desc;
    private String status;
    private int createdBy;
    private int assignedTo;
    private Date createdAt;
    private Date lastUpdatedAt;
    private int deadline;
    private int priority;
    private List<Comment> comments;
}
