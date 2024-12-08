package com.backend.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.dto.TaskDto;
import com.backend.entity.Task;
import com.backend.exception.ResourceNotFoundException;
import com.backend.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TaskDto createTask(TaskDto taskDto) {
        if (taskDto.getCreatedAt() == null) {
            taskDto.setCreatedAt(new Date());
        }
        Task newTask = dtoToTask(taskDto);
        this.taskRepo.save(newTask);
        return taskToDto(newTask);
    }

    @Override
    public TaskDto updateTask(TaskDto taskDto, Integer taskId) throws ResourceNotFoundException {
        Task foundTask = this.taskRepo.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
        
        foundTask.setTitle(taskDto.getTitle());
        foundTask.setDescrString(taskDto.getDescrString());
        foundTask.setStatus(taskDto.getStatus());
        foundTask.setAssignedTo(taskDto.getAssignedTo());
        foundTask.setLastUpdatedAt(new Date());
        foundTask.setDeadline(taskDto.getDeadline());
        foundTask.setPriority(taskDto.getPriority());

        Task updatedTask = this.taskRepo.save(foundTask);
        return taskToDto(updatedTask);
    }

    @Override
    public TaskDto getTaskById(Integer taskId) throws ResourceNotFoundException {
        Task foundTask = this.taskRepo.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
        return taskToDto(foundTask);
    }

    @Override
    public List<TaskDto> getAllTasks() {
        List<Task> tasks = this.taskRepo.findAll();
        return tasks.stream().map(this::taskToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteTask(Integer taskId) {
        Task foundTask = this.taskRepo.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task", "id", taskId));
        this.taskRepo.delete(foundTask);
    }

    private Task dtoToTask(TaskDto taskDto) {
        return this.modelMapper.map(taskDto, Task.class);
    }

    private TaskDto taskToDto(Task task) {
        return this.modelMapper.map(task, TaskDto.class);
    }
}
