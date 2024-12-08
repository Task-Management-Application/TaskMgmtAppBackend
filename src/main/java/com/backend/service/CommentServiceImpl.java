package com.backend.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.dto.CommentDto;
import com.backend.entity.Comment;
import com.backend.exception.ResourceNotFoundException;
import com.backend.repository.CommentRepository;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto) {
        if (commentDto.getTimestamp() == null) {
            commentDto.setTimestamp(new Date());
        }
        Comment newComment = dtoToComment(commentDto);
        this.commentRepo.save(newComment);
        return commentToDto(newComment);
    }

    @Override
    public CommentDto updateComment(CommentDto commentDto, Integer commentId) throws ResourceNotFoundException {
        Comment foundComment = this.commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        
        foundComment.setUserId(commentDto.getUserId());
        foundComment.setComment(commentDto.getComment());
        foundComment.setTaskId(commentDto.getTaskId());
        foundComment.setTimestamp(commentDto.getTimestamp());
        foundComment.setAttachmentURL(commentDto.getAttachmentURL());

        Comment updatedComment = this.commentRepo.save(foundComment);
        return commentToDto(updatedComment);
    }

    @Override
    public CommentDto getCommentById(Integer commentId) throws ResourceNotFoundException {
        Comment foundComment = this.commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        return commentToDto(foundComment);
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = this.commentRepo.findAll();
        return comments.stream().map(this::commentToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment foundComment = this.commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
        this.commentRepo.delete(foundComment);
    }

    private Comment dtoToComment(CommentDto commentDto) {
        return this.modelMapper.map(commentDto, Comment.class);
    }

    private CommentDto commentToDto(Comment comment) {
        return this.modelMapper.map(comment, CommentDto.class);
    }
}
