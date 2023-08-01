package com.service.adminstration.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.service.adminstration.entities.Department;
import com.service.adminstration.exception.DepartmentNotFoundException;
import com.service.adminstration.repository.DepartmentRepo;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private DepartmentRepo departmentRepo;

    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    public void setup() {
        // MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetDepartments() {
        // Mock data
        List<Department> departments = new ArrayList<>();
        departments.add(new Department(1, "Department 1"));
        departments.add(new Department(2, "Department 2"));

        when(departmentRepo.findAll()).thenReturn(departments);

        // Test
        List<Department> result = departmentService.getDepartments();

        // Assertion
        assertEquals(departments, result);
        verify(departmentRepo, times(1)).findAll();
    }

    @Test
    public void testSaveDepartment() {
        // Mock data
        Department department = new Department(1, "New Department");

        when(departmentRepo.save(any(Department.class))).thenReturn(department);

        // Test
        Department result = departmentService.saveDepartment(department);

        // Assertion
        assertEquals(department, result);
        verify(departmentRepo, times(1)).save(department);
    }

    @Test
    public void testUpdateDepartment() throws DepartmentNotFoundException {
        // Mock data
        int id = 1;
        Department existingDepartment = new Department(id, "Department 1");
        Department updatedDepartment = new Department(id, "Updated Department");

        when(departmentRepo.findById(id)).thenReturn(Optional.of(existingDepartment));
        when(departmentRepo.save(any(Department.class))).thenReturn(updatedDepartment);

        // Test
        Department result = departmentService.updateDepartment(updatedDepartment, id);

        // Assertion
        assertEquals(updatedDepartment, result);
        verify(departmentRepo, times(1)).findById(id);
        verify(departmentRepo, times(1)).save(existingDepartment);
    }

    @Test
    public void testUpdateDepartment_DepartmentNotFound() {
        // Mock data
        int id = 1;
        Department updatedDepartment = new Department(id, "Updated Department");

        when(departmentRepo.findById(id)).thenReturn(Optional.empty());

        // Test and Assertion
        assertThrows(DepartmentNotFoundException.class, () -> {
            departmentService.updateDepartment(updatedDepartment, id);
        });
        verify(departmentRepo, times(1)).findById(id);
        verify(departmentRepo, never()).save(any(Department.class));
    }

    @Test
    public void testDeleteDepartment() throws DepartmentNotFoundException {
        // Mock data
        int id = 1;
        Department existingDepartment = new Department(id, "Department 1");

        when(departmentRepo.findById(id)).thenReturn(Optional.of(existingDepartment));

        // Test
        boolean result = departmentService.deleteDepartment(id);

        // Assertion
        assertTrue(result);
        verify(departmentRepo, times(1)).findById(id);
        verify(departmentRepo, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteDepartment_DepartmentNotFound() {
        // Mock data
        int id = 1;

        when(departmentRepo.findById(id)).thenReturn(Optional.empty());

        // Test and Assertion
        assertThrows(DepartmentNotFoundException.class, () -> {
            departmentService.deleteDepartment(id);
        });
        verify(departmentRepo, times(1)).findById(id);
        verify(departmentRepo, never()).deleteById(anyInt());
    }
}
