package com.backend.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.converter.CommentConverter;
import com.backend.converter.TaskConverter;
import com.backend.dto.CommentDto;
import com.backend.dto.TaskDto;
import com.backend.entity.Comment;
import com.backend.entity.Task;
import com.backend.exception.ResourceNotFoundException;
import com.backend.repository.TaskRepository;
import com.backend.repository.UserRepository;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CommentConverter commentConverter;

    @Autowired
    private TaskConverter taskConverter;
    @Override
    public TaskDto createTask(TaskDto taskDto) {
        if (taskDto.getCreatedAt() == null) {
            taskDto.setCreatedAt(new Date());
        }
        Task newTask = taskConverter.TaskDtoToTask(taskDto);
        this.taskRepo.save(newTask);
        return taskConverter.TaskToTaskDto(newTask);
    }

    @Override
    public TaskDto updateTask(TaskDto taskDto, Integer taskId) throws ResourceNotFoundException {
        Task foundTask = this.taskRepo.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
        
        foundTask.setTitle(taskDto.getTitle());
        foundTask.setDescrString(taskDto.getDescrString());
        foundTask.setStatus(taskDto.getStatus());
        foundTask.setAssignedTo(this.userRepo.findById(taskDto.getAssignedTo())
                .orElseThrow(()->new ResourceNotFoundException("User", "Id", taskDto.getAssignedTo())));
        foundTask.setLastUpdatedAt(new Date());
        foundTask.setDeadline(taskDto.getDeadline());
        foundTask.setPriority(taskDto.getPriority());

        Task updatedTask = this.taskRepo.save(foundTask);
        return taskConverter.TaskToTaskDto(updatedTask);
    }

    @Override
    public TaskDto getTaskById(Integer taskId) throws ResourceNotFoundException {
        Task foundTask = this.taskRepo.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
        return taskConverter.TaskToTaskDto(foundTask);
    }

    @Override
    public List<TaskDto> getAllTasks() {
        List<Task> tasks = this.taskRepo.findAll();
        return tasks.stream().map(task -> taskConverter.TaskToTaskDto(task)).collect(Collectors.toList());
    }

    @Override
    public void deleteTask(Integer taskId) {
        Task foundTask = this.taskRepo.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
        this.taskRepo.delete(foundTask);
    }
    @Override
    public List<CommentDto> getAllCommentByTaskId(Integer taskId)
    {
        Task foundTask = this.taskRepo.findById(taskId)
                .orElseThrow(()->new ResourceNotFoundException("Task", "Id", taskId));
        List<Comment> commentList = foundTask.getComments();
        return commentList.stream().map(comment -> commentConverter.commentToCommentDto(comment)).collect(Collectors.toList());

    }
}
