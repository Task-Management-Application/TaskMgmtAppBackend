package com.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.dto.CommentDto;
import com.backend.dto.TaskDto;
import com.backend.entity.Task;
import com.backend.exception.ResourceNotFoundException;

public interface TaskService {
    TaskDto createTask(TaskDto task);
    
    TaskDto updateTask(TaskDto task, Integer taskId) throws ResourceNotFoundException;

    TaskDto getTaskById(Integer taskId);

    List<TaskDto> getAllTasks();

    void deleteTask(Integer taskId);

    List<CommentDto> getAllCommentByTaskId(Integer taskId);
}
