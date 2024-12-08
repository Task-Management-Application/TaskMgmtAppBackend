package com.backend.converter;

import java.beans.JavaBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.backend.dto.OrganisationDto;
import com.backend.entity.Organisation;
import com.backend.exception.ResourceNotFoundException;
import com.backend.repository.OrganisationRepository;
import com.backend.repository.OrganisationRepository;
import com.backend.repository.UserRepository;
@Component
public class OrganisationConverter {
    @Autowired
    private UserRepository userRepo;

    public Organisation OrganisationDtoToOrganisation(OrganisationDto organisationDto)
    {
        Organisation newOrganisation = new Organisation();
        newOrganisation.setCreatedAt(organisationDto.getCreatedAt());
        newOrganisation.setCreatedBy(organisationDto.getCreatedBy());
        newOrganisation.setOrgName(organisationDto.getOrgName());
        return newOrganisation;
    }

    public OrganisationDto OrganisationToOrganisationDto(Organisation organisation)
    {
        OrganisationDto newDto = new OrganisationDto();
        newDto.setCreatedAt(organisation.getCreatedAt());
        newDto.setCreatedBy(organisation.getCreatedBy());
        newDto.setOrgName(organisation.getOrgName());
        newDto.setOrgId(organisation.getOrgId());
        return newDto;
    }
}
