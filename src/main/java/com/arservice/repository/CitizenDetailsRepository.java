package com.arservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.arservice.entity.CitizenDetailsEntity;

public interface CitizenDetailsRepository extends JpaRepository<CitizenDetailsEntity, Integer> {

}
