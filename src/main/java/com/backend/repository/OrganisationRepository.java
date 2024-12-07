package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.entity.Organisation;

public interface OrganisationRepository extends JpaRepository<Organisation,Integer> {
}
