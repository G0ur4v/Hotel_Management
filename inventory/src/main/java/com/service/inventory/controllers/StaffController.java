package com.service.inventory.controllers;

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

import com.service.inventory.entities.Response;
import com.service.inventory.entities.Staff;
import com.service.inventory.exception.StaffNotFoundException;
import com.service.inventory.services.StaffService;

@RestController
@RequestMapping("/inventory")
public class StaffController {

  @Autowired
  private StaffService staffService;

  // add staff
  @PostMapping("/addStaff")
  public Response addStaff(@RequestBody Staff staff) {

    Staff result = staffService.addStaff(staff);
    if (result != null) {
      return Response.builder()
          .success(true)
          .staff(result)
          .message("Staff added successfully")
          .code(HttpStatus.SC_OK)
          .build();
    } else {
      return Response.builder()
          .success(false)
          .message("Staff not saved successfully/Department not exist")
          .staff(staff)
          .code(HttpStatus.SC_BAD_REQUEST)
          .build();
    }
  }

  // update staff
  @PostMapping("/updateStaff/{id}")
  public Response updateStaffResponse(@RequestBody Staff staff, @PathVariable int id) {
    return updateStaff(staff, id);
  }

  @PutMapping("/updateStaff/{id}")
  public Response updateStaff(@RequestBody Staff staff, @PathVariable int id) {
    try {
      Staff result = staffService.updateStaff(staff, id);
      if (result != null) {
        return Response.builder()
            .success(true)
            .staff(result)
            .message("Staff updated successfully")
            .code(HttpStatus.SC_OK)
            .build();
      } else {
        return Response.builder()
            .success(false)
            .staff(result)
            .message("staff not updated/Department id not found")
            .code(HttpStatus.SC_BAD_REQUEST)
            .build();
      }

    } catch (StaffNotFoundException e) {
      return Response.builder()
          .success(false)
          .staff(staff)
          .message(e.getMessage())
          .code(HttpStatus.SC_BAD_REQUEST)
          .build();
    }

  }

  // delete Staff
  @GetMapping("/deleteStaff/{id}")
  public Response deleteStaffResponse(@PathVariable int id) {
    return deleteStaff(id);
  }

  @DeleteMapping("/deleteStaff/{id}")
  public Response deleteStaff(@PathVariable int id) {
    try {
      Staff result = staffService.deleteStaff(id);
      return Response.builder()
          .success(true)
          .staff(result)
          .message("Staff deleted successfully")
          .code(HttpStatus.SC_OK)
          .build();
    } catch (StaffNotFoundException e) {

      return Response.builder()
          .success(false)
          .staff(null)
          .message(e.getMessage())
          .code(HttpStatus.SC_BAD_REQUEST)
          .build();
    }
  }

  // view all staff
  @GetMapping("/getAllStaff")
  public List<Staff> getAllStaff() {
    return staffService.getAllStaff();
  }

}
