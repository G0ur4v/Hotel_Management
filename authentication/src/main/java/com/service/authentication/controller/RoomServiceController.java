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
@RequestMapping("/room")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_RECEPTION')")
public class RoomServiceController {

    @Autowired
    RestTemplate restTemplate;

    private static final String ROOMSERVICE_BASE_URL = "http://localhost:8000/room";

    @PostMapping("/addRoomService")
    public Response addRoomService(@RequestBody Object roomService) {
        return restTemplate.postForObject(ROOMSERVICE_BASE_URL + "/addRoomService", roomService, Response.class);

    }

    @PutMapping("/updateRoomService/{serviceId}")
    public Response updateRoomService(@RequestBody Object roomService, @PathVariable int serviceId) {
        return restTemplate.postForObject(ROOMSERVICE_BASE_URL + "/updateRoomService/{serviceId}", roomService,
                Response.class, serviceId);
    }

    @DeleteMapping("/deleteRoomService/{serviceId}")
    public Response deleteRoomService(@PathVariable int serviceId) {
        return restTemplate.getForObject(ROOMSERVICE_BASE_URL + "/deleteRoomService/{serviceId}", Response.class,
                serviceId);

    }

    @GetMapping("/getActiveRoomServices")
    public List<Object> getActiveRoomServices() {
        Object[] services = restTemplate.getForObject(ROOMSERVICE_BASE_URL + "/getActiveRoomServices", Object[].class);
        return Arrays.asList(services);
    }

    @GetMapping("/getRoomService/{roomNumber}/{completed}")
    public Response getRoomServiceByRoomNumberAndIsCompleted(@PathVariable String roomNumber,
            @PathVariable boolean completed) {
        return restTemplate.getForObject(ROOMSERVICE_BASE_URL + "/getRoomService/{roomNumber}/{completed}",
                Response.class, roomNumber, completed);
    }

    // delete ordered item by id
    @DeleteMapping("/deleteOrderedItemById/{id}")
    public void deleteOrderedItemById(@PathVariable int id) {
        restTemplate.delete(ROOMSERVICE_BASE_URL + "/deleteOrderedItemById/{id}",
                id);
    }
}
