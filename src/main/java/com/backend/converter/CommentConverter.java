package com.backend.converter;

import java.beans.JavaBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.backend.dto.CommentDto;
import com.backend.entity.Comment;
import com.backend.exception.ResourceNotFoundException;
import com.backend.repository.TaskRepository;
import com.backend.repository.UserRepository;
@Component
public class CommentConverter {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TaskRepository taskRepo;

    public Comment commentDtoToComment(CommentDto commentDto)
    {
        Comment newComment = new Comment();
        newComment.setUser(this.userRepo.findById(commentDto.getUserId())
                .orElseThrow(()->new ResourceNotFoundException("User", "Id", commentDto.getUserId())));
        newComment.setAttachmentURL(commentDto.getAttachmentURL());
        newComment.setTask(this.taskRepo.findById(commentDto.getTaskId())
                .orElseThrow(()->new ResourceNotFoundException("Task", "Id", commentDto.getTaskId())));
        newComment.setComment(commentDto.getComment());
        newComment.setTimestamp(commentDto.getTimestamp());
        return newComment;
    }

    public CommentDto commentToCommentDto(Comment comment)
    {
        CommentDto newDto = new CommentDto();
        newDto.setUserId(comment.getUser().getUserId());
        newDto.setComment(comment.getComment());
        newDto.setAttachmentURL(comment.getAttachmentURL());
        newDto.setTaskId(comment.getTask().getTaskId());
        newDto.setTimestamp(comment.getTimestamp());
        newDto.setCommentId(comment.getCommentId());
        return newDto;
    }
}
