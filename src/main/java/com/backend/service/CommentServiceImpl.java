package com.backend.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.backend.converter.CommentConverter;
import com.backend.dto.CommentDto;
import com.backend.entity.Comment;
import com.backend.exception.ResourceNotFoundException;
import com.backend.repository.CommentRepository;
import com.backend.repository.TaskRepository;
import com.backend.repository.UserRepository;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CommentConverter commentConverter;

    @Override
    public CommentDto createComment(CommentDto commentDto) {
        if (commentDto.getTimestamp() == null) {
            commentDto.setTimestamp(new Date());
        }
        Comment newComment = commentConverter.commentDtoToComment(commentDto);
        this.commentRepo.save(newComment);
        return commentConverter.commentToCommentDto(newComment);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Integer commentId) throws ResourceNotFoundException {
        Comment foundComment = this.commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        
        foundComment.setUser(this.userRepo.findById(commentDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", commentDto.getUserId())));
        foundComment.setComment(commentDto.getComment());
        foundComment.setTask(this.taskRepo.findById(commentDto.getTaskId()).orElseThrow(()->new ResourceNotFoundException("Task", "Id", commentDto.getTaskId())));
        foundComment.setTimestamp(commentDto.getTimestamp());
        foundComment.setAttachmentURL(commentDto.getAttachmentURL());

        Comment updatedComment = this.commentRepo.save(foundComment);
        return commentConverter.commentToCommentDto(updatedComment);
    }

    @Override
    public CommentDto getCommentById(Integer commentId) throws ResourceNotFoundException {
        Comment foundComment = this.commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        return commentConverter.commentToCommentDto(foundComment);
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = this.commentRepo.findAll();
        return comments.stream().map(comment -> commentConverter.commentToCommentDto(comment)).collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment foundComment = this.commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        this.commentRepo.delete(foundComment);
    }
}
