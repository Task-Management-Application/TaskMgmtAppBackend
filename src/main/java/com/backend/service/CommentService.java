package com.backend.service;

import java.util.List;

import com.backend.dto.CommentDto;
import com.backend.exception.ResourceNotFoundException;

public interface CommentService {
    CommentDto createComment(CommentDto commentId);
    
    CommentDto updateComment(CommentDto comment, Integer commentId) throws ResourceNotFoundException;

    CommentDto getCommentById(Integer commentId);

    List<CommentDto> getAllComments();

    void deleteComment(Integer commentId);
}
