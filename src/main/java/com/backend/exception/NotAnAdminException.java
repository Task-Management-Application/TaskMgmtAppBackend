package com.backend.exception;

import lombok.*;

@Getter
@Setter
public class NotAnAdminException extends RuntimeException {
    Integer userId;
    Integer orgId;

    public NotAnAdminException(Integer userId, Integer orgId)
    {
        super(String.format("User with id = %s is not an admin of the organisation with id = %s", userId, orgId));
        this.userId = userId;
        this.orgId = orgId;
    }
}