package com.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.dto.OrganisationDto;
import com.backend.dto.TaskDto;
import com.backend.dto.UserDto;
import com.backend.entity.User;
import com.backend.exception.ResourceNotFoundException;

public interface OrganisationService {
    OrganisationDto createOrganisation(OrganisationDto organisation);
    
    OrganisationDto updateOrganisation(OrganisationDto organisation, Integer organisationId) throws ResourceNotFoundException;

    OrganisationDto getOrganisationById(Integer organisationId);

    List<OrganisationDto> getAllOrganisations();

    void deleteOrganisation(Integer organisationId);

    List<TaskDto> getAllTasksByOrgId(Integer organisationId);

    List<UserDto> getAllUsersByOrgId(Integer organisationId);
}
