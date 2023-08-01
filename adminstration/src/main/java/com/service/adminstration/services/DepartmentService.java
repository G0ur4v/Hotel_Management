package com.service.adminstration.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.adminstration.entities.Department;
import com.service.adminstration.exception.DepartmentNotFoundException;
import com.service.adminstration.repository.DepartmentRepo;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepo departmentRepo;

    // get All the departments
    public List<Department> getDepartments() {
        return departmentRepo.findAll();
    }

    // get department by id
    public Department getDepartmentById(int departmentId) {
       return departmentRepo.findById(departmentId).orElse(null);
    }

    // saving department
    public Department saveDepartment(Department department) {

        return departmentRepo.save(department);
    }

    // updating department
    public Department updateDepartment(Department department, int id) throws DepartmentNotFoundException {
        Department oldDepartment = departmentRepo.findById(id).orElse(null);

        if (oldDepartment != null) {
            oldDepartment.setName(department.getName());
            oldDepartment.setDescription(department.getDescription());
            departmentRepo.save(oldDepartment);
            return oldDepartment;
        } else {
            throw new DepartmentNotFoundException("Department not found");
        }
    }

    // deleting the department
    public boolean deleteDepartment(int id) throws DepartmentNotFoundException {
        Department oldDepartment = departmentRepo.findById(id).orElse(null);
        if (oldDepartment != null) {
            departmentRepo.deleteById(id);
            return true;
        } else {
            throw new DepartmentNotFoundException("Department not found");
        }
    }
}
