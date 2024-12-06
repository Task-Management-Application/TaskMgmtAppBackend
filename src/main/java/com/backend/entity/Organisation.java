package com.backend.entity;

import java.util.Date;
import java.util.List;

public class Organisation {
    private int orgId;
    private String orgName;
    private int createdBy;
    private Date createdAt;
    private List<User> users;
    private List<Task> tasks;
}
