package com.service.authentication.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.service.authentication.entities.Response;

@RestController
@RequestMapping("/department")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class DepartmentController {

    @Autowired
    RestTemplate restTemplate;

    private static final String DEPT_BASE_URL = "http://localhost:8000/department";

    @GetMapping("/report")

    public String getReports() {

        return restTemplate.getForObject(DEPT_BASE_URL + "/report", String.class);
    }

    @GetMapping("/get")
    public List<Object> getDepartments() {
        Object[] departments = restTemplate.getForObject(DEPT_BASE_URL + "/get", Object[].class);
        return Arrays.asList(departments);
    }

    @PostMapping("/save")

    public Response saveDepartment(@RequestBody Object department) {
        return restTemplate.postForObject(DEPT_BASE_URL + "/save", department, Response.class);

    }

    @PutMapping("/update/{id}")
    public Response updateDepartment(@RequestBody Object department, @PathVariable int id) {
        return restTemplate.postForObject(DEPT_BASE_URL + "/update/{id}", department, Response.class, id);

    }

    @DeleteMapping("/delete/{id}")
    public Response deleteDepartment(@PathVariable int id) {
        return restTemplate.getForObject(DEPT_BASE_URL + "/delete/{id}", Response.class, id);

    }
}
