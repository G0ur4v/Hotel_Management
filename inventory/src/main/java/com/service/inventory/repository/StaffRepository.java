package com.service.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.inventory.entities.Staff;

public interface StaffRepository extends JpaRepository<Staff, Integer> {

}
