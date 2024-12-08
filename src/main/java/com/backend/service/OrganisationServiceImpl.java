package com.backend.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.dto.OrganisationDto;
import com.backend.entity.Organisation;
import com.backend.exception.ResourceNotFoundException;
import com.backend.repository.OrganisationRepository;


@Service
public class OrganisationServiceImpl implements OrganisationService {
    @Autowired
    private OrganisationRepository organisationRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public OrganisationDto createOrganisation(OrganisationDto organisation)
    {
        if(organisation.getCreatedAt()==null)
            organisation.setCreatedAt(new Date());
        Organisation newOrganisation = dtoToOrganisation(organisation);
        this.organisationRepo.save(newOrganisation);
        return OrganisationToDto(newOrganisation);
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
        return OrganisationToDto(updatedOrganisation);
    }

    @Override
    public OrganisationDto getOrganisationById(Integer organisationId) throws ResourceNotFoundException
    {
        Organisation foundOrganisation = this.organisationRepo.findById(organisationId)
                .orElseThrow(()-> new ResourceNotFoundException("Organisation", "id", organisationId));
        return OrganisationToDto(foundOrganisation);
    }

    @Override
    public List<OrganisationDto> getAllOrganisations()
    {
        List<Organisation> organisations = this.organisationRepo.findAll();
        return organisations.stream().map(Organisation->this.OrganisationToDto(Organisation)).collect(Collectors.toList());
    }

    @Override
    public void deleteOrganisation(Integer organisationId)
    {
        Organisation foundOrganisation = this.organisationRepo.findById(organisationId)
                .orElseThrow(()-> new ResourceNotFoundException("Organisation", "id", organisationId));
        this.organisationRepo.delete(foundOrganisation);
    }

    private Organisation dtoToOrganisation(OrganisationDto organisationDto) {
        return this.modelMapper.map(organisationDto, Organisation.class);
    }

    private OrganisationDto OrganisationToDto(Organisation organisation) {
        return this.modelMapper.map(organisation,OrganisationDto.class);
    }
}