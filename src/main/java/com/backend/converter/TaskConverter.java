package com.backend.converter;

import java.beans.JavaBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.backend.dto.TaskDto;
import com.backend.entity.Task;
import com.backend.exception.ResourceNotFoundException;
import com.backend.repository.OrganisationRepository;
import com.backend.repository.TaskRepository;
import com.backend.repository.UserRepository;
@Component
public class TaskConverter {
    @Autowired
    private OrganisationRepository orgRepo;

    @Autowired
    private UserRepository userRepo;

    public Task TaskDtoToTask(TaskDto taskDto)
    {
        Task newTask = new Task();
        newTask.setAssignedTo(userRepo.findById(taskDto.getAssignedTo())
                .orElseThrow(()-> new ResourceNotFoundException("User", "Id", taskDto.getAssignedTo())));
        newTask.setCreatedAt(taskDto.getCreatedAt());
        newTask.setCreatedBy(userRepo.findById(taskDto.getCreatedBy())
        .orElseThrow(()-> new ResourceNotFoundException("User", "Id", taskDto.getCreatedBy())));
        newTask.setDeadline(taskDto.getDeadline());
        newTask.setDescrString(taskDto.getDescrString());
        newTask.setLastUpdatedAt(taskDto.getLastUpdatedAt());
        newTask.setPriority(taskDto.getPriority());
        newTask.setStatus(taskDto.getStatus());
        newTask.setTitle(taskDto.getTitle());
        newTask.setOrganisation(this.orgRepo.findById(taskDto.getOrganisationId())
                .orElseThrow(()->new ResourceNotFoundException("Organisation","Id",taskDto.getOrganisationId())));
        return newTask;
    }

    public TaskDto TaskToTaskDto(Task task)
    {
        TaskDto newDto = new TaskDto();
        newDto.setAssignedTo(task.getAssignedTo().getUserId());
        newDto.setCreatedAt(task.getCreatedAt());
        newDto.setCreatedBy(task.getCreatedBy().getUserId());
        newDto.setDeadline(task.getDeadline());
        newDto.setDescrString(task.getDescrString());
        newDto.setLastUpdatedAt(task.getLastUpdatedAt());
        newDto.setPriority(task.getPriority());
        newDto.setStatus(task.getStatus());
        newDto.setTitle(task.getTitle());
        newDto.setTaskId(task.getTaskId());
        newDto.setOrganisationId(task.getOrganisation().getOrgId());
        return newDto;
    }
}
