package com.service.room.controllers;

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

import com.service.room.entities.Response;
import com.service.room.entities.RoomService;
import com.service.room.exception.RoomServiceAlreadyExists;
import com.service.room.exception.RoomServiceNotFound;
import com.service.room.services.RoomServices;

@RestController
@RequestMapping("/room")
public class RoomServiceController {

    @Autowired
    private RoomServices service;

    // add room service
    @PostMapping("/addRoomService")
    public Response addRoomService(@RequestBody RoomService roomService) {
        RoomService result;
        try {
            result = service.addRoomService(roomService);

            return Response.builder()
                    .success(true)
                    .roomService(result)
                    .message("RoomService Started successfully")
                    .code(HttpStatus.SC_OK)
                    .build();
        } catch (RoomServiceAlreadyExists e) {
            return Response.builder()
                    .success(false)
                    .message(e.getMessage())
                    .roomService(null)
                    .code(HttpStatus.SC_BAD_REQUEST)
                    .build();

        }

    }

    // update room service
    @PostMapping("/updateRoomService/{serviceId}")
    public Response updateRoomServiceResponse(@RequestBody RoomService roomService, @PathVariable int serviceId) {
        return updateRoomService(roomService, serviceId);
    }

    @PutMapping("/updateRoomService/{serviceId}")
    public Response updateRoomService(@RequestBody RoomService roomService, @PathVariable int serviceId) {

        try {

            RoomService result = service.updateRoomService(roomService, serviceId);
            return Response.builder()
                    .success(true)
                    .roomService(result)
                    .message("RoomService updated successfully")
                    .code(HttpStatus.SC_OK)
                    .build();
        } catch (RoomServiceNotFound e) {
            return Response.builder()
                    .success(false)
                    .message(e.getMessage())
                    .roomService(roomService)
                    .code(HttpStatus.SC_BAD_REQUEST)
                    .build();

        }
    }

    // delete room service
    @GetMapping("/deleteRoomService/{serviceId}")
    public Response deleteRoomServiceResponse(@PathVariable int serviceId) {
        return deleteRoomService(serviceId);
    }

    @DeleteMapping("/deleteRoomService/{serviceId}")
    public Response deleteRoomService(@PathVariable int serviceId) {
        try {
            RoomService result = service.deleteRoomService(serviceId);
            return Response.builder()
                    .success(true)
                    .roomService(result)
                    .message("RoomService deleted successfully")
                    .code(HttpStatus.SC_OK)
                    .build();
        } catch (RoomServiceNotFound e) {
            return Response.builder()
                    .success(false)
                    .message(e.getMessage())
                    .roomService(null)
                    .code(HttpStatus.SC_BAD_REQUEST)
                    .build();
        }
    }

    // get roomservice by room number and completed status
    @GetMapping("/getRoomService/{roomNumber}/{completed}")
    public Response getRoomServiceByRoomNumberAndIsCompleted(@PathVariable String roomNumber,
            @PathVariable boolean completed) {

        try {
            RoomService result = service.getRoomServiceByRoomNumberAndIsCompleted(roomNumber, completed);
            return Response.builder()
                    .success(true)
                    .roomService(result)
                    .message("Room Service fetched successfully")
                    .code(HttpStatus.SC_OK)
                    .build();
        } catch (RoomServiceNotFound e) {
            return Response.builder()
                    .success(false)
                    .message(e.getMessage())
                    .roomService(null)
                    .code(HttpStatus.SC_BAD_REQUEST)
                    .build();

        }

    }

    // set roomservice completed by roomNumber
    @PostMapping("/setRoomServiceCompleted/{roomNumber}/{completed}")
    public Response setRoomServiceCompleted(@PathVariable String roomNumber, @PathVariable boolean completed) {
        try {
            boolean result = service.setServiceCompleted(roomNumber, completed);
            return Response.builder()
                    .success(result)
                    .roomService(null)
                    .message("Room Service completed successfully")
                    .code(HttpStatus.SC_OK)
                    .build();
        } catch (RoomServiceNotFound e) {
            return Response.builder()
                    .success(false)
                    .message(e.getMessage())
                    .roomService(null)
                    .code(HttpStatus.SC_BAD_REQUEST)
                    .build();
        }
    }

    // get ActiveRoomServices
    @GetMapping("/getActiveRoomServices")
    public List<RoomService> getActiveRoomServices() {
        return service.getActiveRoomServices();
    }

    // delete ordered item by id
    @DeleteMapping("/deleteOrderedItemById/{id}")
    public void deleteOrderedItemById(@PathVariable int id) {
        service.deleteOrderedItemById(id);
    }
}
