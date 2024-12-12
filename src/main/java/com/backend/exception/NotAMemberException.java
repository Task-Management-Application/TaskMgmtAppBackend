package com.backend.exception;

import lombok.*;

@Getter
@Setter
public class NotAMemberException extends RuntimeException {
    Integer userId;
    Integer orgId;

    public NotAMemberException(Integer userId, Integer orgId)
    {
        super(String.format("User with id = %s is not a member of the organisation with id = %s", userId, orgId));
        this.userId = userId;
        this.orgId = orgId;
    }
}