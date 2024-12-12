package com.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException.NotImplemented;

import com.backend.entity.Organisation;
import com.backend.entity.User;
import com.backend.exception.ResourceNotFoundException;
import com.backend.repository.OrganisationRepository;
import com.backend.repository.UserRepository;

@Service
public class AdminManagementService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganisationRepository organisationRepository;

    public boolean checkAdminPrivilegeForUser(Integer userId, Integer orgId)
    {
        User foundUser = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
        Organisation foundOrganisation = organisationRepository.findById(orgId).orElseThrow(()->new ResourceNotFoundException("Organisation", "Id", orgId));
        Boolean isAdmin = foundOrganisation.isAdmin(foundUser);
        return isAdmin==null? false:isAdmin;
    }

    public void updateAdmin(Integer userId, Integer orgId, Boolean makeValue)
    {
        User foundUser = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
        Organisation foundOrganisation = organisationRepository.findById(orgId).orElseThrow(()->new ResourceNotFoundException("Organisation", "Id", orgId));
        foundOrganisation.makeAdmin(foundUser, makeValue);
        
        System.out.println("updating the admin for "+userId+orgId+ makeValue);
        organisationRepository.save(foundOrganisation);
    }
}
