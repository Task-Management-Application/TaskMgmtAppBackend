package com.backend.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.backend.converter.OrganisationConverter;
import com.backend.converter.TaskConverter;
import com.backend.converter.UserConverter;
import com.backend.dto.OrganisationDto;
import com.backend.dto.TaskDto;
import com.backend.dto.UserDto;
import com.backend.entity.Organisation;
import com.backend.entity.Task;
import com.backend.entity.User;
import com.backend.exception.ResourceNotFoundException;
import com.backend.repository.OrganisationRepository;
import com.backend.repository.UserRepository;


@Service
public class OrganisationServiceImpl implements OrganisationService {
    @Autowired
    private OrganisationRepository organisationRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private OrganisationConverter orgConverter;

    @Autowired
    private TaskConverter taskConverter;

    @Override
    public OrganisationDto createOrganisation(OrganisationDto organisation)
    {
        if(organisation.getCreatedAt()==null)
            organisation.setCreatedAt(new Date());
        Organisation newOrganisation = orgConverter.OrganisationDtoToOrganisation(organisation);
        this.organisationRepo.save(newOrganisation);
        
        OrganisationDto newOrganisationDto = orgConverter.OrganisationToOrganisationDto(newOrganisation);
        User foundUser = this.userRepo.findById(newOrganisationDto.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", newOrganisationDto.getCreatedBy()));
        userService.addUserToOrganisation(foundUser.getUserId(), newOrganisationDto.getOrgId());
        return newOrganisationDto;
    }

    @Override
    public OrganisationDto updateOrganisation(OrganisationDto organisation, Integer organisationId) throws ResourceNotFoundException
    {
        Organisation foundOrganisation = this.organisationRepo.findById(organisationId)
                .orElseThrow(()-> new ResourceNotFoundException("Organisation", "id", organisationId));
        foundOrganisation.setOrgName(organisation.getOrgName());
        foundOrganisation.setCreatedAt(organisation.getCreatedAt());
        foundOrganisation.setCreatedBy(organisation.getCreatedBy());
        Organisation updatedOrganisation = this.organisationRepo.save(foundOrganisation);
        return orgConverter.OrganisationToOrganisationDto(updatedOrganisation);
    }

    @Override
    public OrganisationDto getOrganisationById(Integer organisationId) throws ResourceNotFoundException
    {
        Organisation foundOrganisation = this.organisationRepo.findById(organisationId)
                .orElseThrow(()-> new ResourceNotFoundException("Organisation", "id", organisationId));
        return orgConverter.OrganisationToOrganisationDto(foundOrganisation);
    }

    @Override
    public List<OrganisationDto> getAllOrganisations()
    {
        List<Organisation> organisations = this.organisationRepo.findAll();
        return organisations.stream().map(Organisation->this.orgConverter.OrganisationToOrganisationDto(Organisation)).collect(Collectors.toList());
    }

    @Override
    public void deleteOrganisation(Integer organisationId)
    {
        Organisation foundOrganisation = this.organisationRepo.findById(organisationId)
                .orElseThrow(()-> new ResourceNotFoundException("Organisation", "id", organisationId));
        this.organisationRepo.delete(foundOrganisation);
    }

    @Override
    public List<TaskDto> getAllTasksByOrgId(Integer organisationId)
    {
        Organisation foundOrganisation = this.organisationRepo.findById(organisationId)
                .orElseThrow(()-> new ResourceNotFoundException("Organisation", "id", organisationId));
        List<Task> taskList = foundOrganisation.getTasks();
        return taskList.stream().map(task -> taskConverter.TaskToTaskDto(task)).collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getAllUsersByOrgId(Integer organisationId)
    {
        Organisation foundOrganisation = this.organisationRepo.findById(organisationId)
                .orElseThrow(()-> new ResourceNotFoundException("Organisation", "id", organisationId));
        List<User> userList = foundOrganisation.getUsers();
        return userList.stream().map(user -> userConverter.UserToUserDto(user)).collect(Collectors.toList());

    }
}