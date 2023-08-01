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
@RequestMapping("/inventory")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
public class InventoryController {

    @Autowired
    RestTemplate restTemplate;

    private static final String INVENTORY_BASE_URL = "http://localhost:8000/inventory";

    // ITEM CONTROLLER

    @PostMapping("/addItem")
    public Response addItem(@RequestBody Object item) {
        return restTemplate.postForObject(INVENTORY_BASE_URL + "/addItem", item, Response.class);
    }

    @PutMapping("/updateItem/{id}")
    public Response updateItem(@RequestBody Object item, @PathVariable int id) {
        return restTemplate.postForObject(INVENTORY_BASE_URL + "/updateItem/{id}", item, Response.class, id);

    }

    @DeleteMapping("/deleteItem/{id}")
    public Response deleteItem(@PathVariable int id) {
        return restTemplate.getForObject(INVENTORY_BASE_URL + "/deleteItem/{id}", Response.class, id);

    }

    @GetMapping("/getItems")
    public List<Object> getItems() {
        Object[] items = restTemplate.getForObject(INVENTORY_BASE_URL + "/getItems", Object[].class);
        return Arrays.asList(items);
    }

    @GetMapping("/getItemById/{id}")
    public List<Object> getItemById(@PathVariable int id) {
        Object item = restTemplate.getForObject(INVENTORY_BASE_URL + "/getItemById/{id}", Object.class);
        return Arrays.asList(item);
    }

    // ROOM CONTROLLER
    @PostMapping("/addRoom")
    public Response addRoom(@RequestBody Object room) {
        return restTemplate.postForObject(INVENTORY_BASE_URL + "/addRoom", room, Response.class);
    }

    @PutMapping("/updateRoom/{id}")
    public Response updateRoom(@RequestBody Object room, @PathVariable int id) {
        return restTemplate.postForObject(INVENTORY_BASE_URL + "/updateRoom/{id}", room, Response.class, id);

    }

    @DeleteMapping("/deleteRoom/{id}")
    public Response deleteRoom(@PathVariable int id) {
        return restTemplate.getForObject(INVENTORY_BASE_URL + "/deleteRoom/{id}", Response.class, id);

    }

    @GetMapping("/getAllRooms")
    public List<Object> getAllRooms() {
        Object[] rooms = restTemplate.getForObject(INVENTORY_BASE_URL + "/getAllRooms", Object[].class);
        return Arrays.asList(rooms);
    }

    @GetMapping("/getRoomByRoomNumber/{roomNumber}")
    public Response getRoomByRoomNumber(@PathVariable String roomNumber) {
            return restTemplate.getForObject(INVENTORY_BASE_URL + "/getRoomByRoomNumber/{roomNumber}", Response.class, roomNumber);

    }
 
    // STAFF CONTROLLER
    @PostMapping("/addStaff")
    public Response addStaff(@RequestBody Object staff) {
        return restTemplate.postForObject(INVENTORY_BASE_URL + "/addStaff", staff, Response.class);
    }

    @PutMapping("/updateStaff/{id}")
    public Response updateStaff(@RequestBody Object staff, @PathVariable int id) {
        return restTemplate.postForObject(INVENTORY_BASE_URL + "/updateStaff/{id}", staff, Response.class, id);

    }

    @DeleteMapping("/deleteStaff/{id}")
    public Response deleteStaff(@PathVariable int id) {
        return restTemplate.getForObject(INVENTORY_BASE_URL + "/deleteStaff/{id}", Response.class, id);

    }

    @GetMapping("/getAllStaff")
    public List<Object> getAllStaff() {
        Object[] staffs = restTemplate.getForObject(INVENTORY_BASE_URL + "/getAllStaff", Object[].class);
        return Arrays.asList(staffs);
    }
}
