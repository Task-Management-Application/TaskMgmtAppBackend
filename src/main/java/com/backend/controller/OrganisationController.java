package com.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.dto.OrganisationDto;
import com.backend.response.ApiResponse;
import com.backend.service.OrganisationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/organisations")
public class OrganisationController {
    @Autowired
    OrganisationService organisationService;


    @PostMapping("/")
    public ResponseEntity<OrganisationDto> createOrganisation(@Valid @RequestBody OrganisationDto organisationDto)
    {
        OrganisationDto createdOrganisationDto = this.organisationService.createOrganisation(organisationDto);
        return new ResponseEntity<>(createdOrganisationDto,HttpStatus.CREATED);
    }

    @PutMapping("/{organisationId}")
    public ResponseEntity<OrganisationDto> updateOrganisation(@Valid @RequestBody OrganisationDto organisationDto, @PathVariable Integer organisationId)
    {
        OrganisationDto updatedOrganisation = this.organisationService.updateOrganisation(organisationDto, organisationId);
        return new ResponseEntity<>(updatedOrganisation,HttpStatus.OK);
    }

    @DeleteMapping("/{organisationId}")
    public ResponseEntity<ApiResponse> deleteOrganisation(@PathVariable Integer organisationId)
    {
        this.organisationService.deleteOrganisation(organisationId);
        return new ResponseEntity<>(new ApiResponse("Successfully deleted the entry"),HttpStatus.OK);
    }
    
    @GetMapping("/")
    public ResponseEntity<List<OrganisationDto>> getAllOrganisations()
    {
        return ResponseEntity.ok(this.organisationService.getAllOrganisations());
    }

    @GetMapping("/{organisationId}")
    public ResponseEntity<OrganisationDto> getOrganisationWithId(@PathVariable Integer organisationId)
    {
        return new ResponseEntity<>(this.organisationService.getOrganisationById(organisationId),HttpStatus.OK);
    }
}
