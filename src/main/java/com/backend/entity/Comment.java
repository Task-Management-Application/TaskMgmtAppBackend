package com.backend.entity;

import java.util.Date;

public class Comment {
    private int commentId;
    private int userId;
    private String comment;
    private int taskId;
    private Date timestamp;
    private String attachmentURL;
}
