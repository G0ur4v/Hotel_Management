package com.service.adminstration.controllers;

import java.util.List;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.service.adminstration.entities.Department;
import com.service.adminstration.entities.Report;
import com.service.adminstration.entities.Response;
import com.service.adminstration.exception.DepartmentNotFoundException;
import com.service.adminstration.services.DepartmentService;

@RestController
@RequestMapping("/department")
public class DepartmentController {

  private static final String RESERVATION_BASE_URL = "http://localhost:8000/reservation";

  @Autowired
  private DepartmentService departmentService;

  @Autowired
  private RestTemplate restTemplate;

  // @GetMapping("/report/{days}")
  // public Report getReports(@PathVariable int days) {
  // Report report = restTemplate.getForObject(RESERVATION_BASE_URL +
  // "/getReservationById/{reservationId}", Response.class,
  // reservationId);
  // }

  @GetMapping("/get")
  public List<Department> getDepartments() {
    return departmentService.getDepartments();
  }

  @GetMapping("/getDepartmentById/{departmentId}")
  public Department getDepartmentById(@PathVariable int departmentId) {
    return departmentService.getDepartmentById(departmentId);
  }

  @PostMapping("/save")
  public Response saveDepartment(@RequestBody Department department) {
    System.out.println("-----test------------------------------ " + department.toString());
    Department result = departmentService.saveDepartment(department);
    if (result != null) {
      return Response.builder()
          .success(true)
          .department(result)
          .message("Department saved successfully")
          .code(HttpStatus.SC_OK)
          .build();
    } else {
      return Response.builder()
          .success(false)
          .message("Department not saved successfully")
          .department(department)
          .code(HttpStatus.SC_BAD_REQUEST)
          .build();
    }
  }

  @PostMapping("/update/{id}")
  public Response updateDepartmentResponse(@RequestBody Department department, @PathVariable int id) {
    return updateDepartment(department, id);

  }

  @PutMapping("/update/{id}")
  public Response updateDepartment(@RequestBody Department department, @PathVariable int id) {
    try {
      Department result = departmentService.updateDepartment(department, id);
      return Response.builder()
          .success(true)
          .department(result)
          .message("Department updated successfully")
          .code(HttpStatus.SC_OK)
          .build();
    } catch (DepartmentNotFoundException e) {
      return Response.builder()
          .success(false)
          .department(department)
          .message(e.getMessage())
          .code(HttpStatus.SC_BAD_REQUEST)
          .build();

    }

  }

  @GetMapping("/delete/{id}")
  public Response deleteDepartmentResponse(@PathVariable int id) {
    return deleteDepartment(id);
  }

  @DeleteMapping("/delete/{id}")
  public Response deleteDepartment(@PathVariable int id) {
    try {
      departmentService.deleteDepartment(id);
      return Response.builder()
          .success(true)
          .department(null)
          .message("Department deleted successfully")
          .code(HttpStatus.SC_OK)
          .build();
    } catch (DepartmentNotFoundException e) {

      return Response.builder()
          .success(false)
          .department(null)
          .message(e.getMessage())
          .code(HttpStatus.SC_BAD_REQUEST)
          .build();
    }
  }
}
