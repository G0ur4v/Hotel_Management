package com.service.inventory.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.service.inventory.entities.Staff;
import com.service.inventory.exception.StaffNotFoundException;
import com.service.inventory.repository.StaffRepository;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private RestTemplate restTemplate;

    // add staff member
    public Staff addStaff(Staff staff) {
        int departmentId = staff.getDepartmentId();
        Object departmenObject = restTemplate.getForObject(
                "http://localhost:8000/department/getDepartmentById/{departmentId}", Object.class,
                departmentId);
        if (departmenObject != null) {
            return staffRepository.save(staff);
        } else {
            return null;
        }

    }

    // update staff member
    public Staff updateStaff(Staff staff, int id) throws StaffNotFoundException {
        Staff oldStaff = staffRepository.findById(id).orElse(null);
        int departmentId = staff.getDepartmentId();
        Object departmenObject = restTemplate.getForObject(
                "http://localhost:8000/department/getDepartmentById/{departmentId}", Object.class,
                departmentId);
        if (departmenObject != null) {
            if (oldStaff != null) {
                oldStaff.setName(staff.getName());
                oldStaff.setContactNumber(staff.getContactNumber());
                oldStaff.setDepartmentId(staff.getDepartmentId());
                oldStaff.setEmail(staff.getEmail());
                oldStaff.setPosition(staff.getPosition());
                oldStaff.setWorkDescription(staff.getWorkDescription());
                staffRepository.save(oldStaff);
                return oldStaff;
            } else {
                throw new StaffNotFoundException("Staff not found");
            }
        } else {
            return null;
        }
    }

    // delete staff member
    public Staff deleteStaff(int id) throws StaffNotFoundException {
        Staff staff = staffRepository.findById(id).orElse(null);
        if (staff != null) {
            staffRepository.deleteById(id);
            return staff;
        } else {
            throw new StaffNotFoundException("Staff not found");
        }

    }

    // view all staff
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }
}
