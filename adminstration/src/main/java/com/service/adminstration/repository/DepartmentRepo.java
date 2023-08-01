package com.service.adminstration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.adminstration.entities.Department;

public interface DepartmentRepo extends JpaRepository<Department, Integer> {

}
