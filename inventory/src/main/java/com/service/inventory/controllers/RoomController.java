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
import com.service.inventory.entities.Room;
import com.service.inventory.exception.RoomAlreadyExists;
import com.service.inventory.exception.RoomNotFoundException;
import com.service.inventory.services.RoomService;

@RestController
@RequestMapping("/inventory")
public class RoomController {

    @Autowired
    private RoomService roomService;

    // addRoom
    @PostMapping("/addRoom")
    public Response addRoom(@RequestBody Room room) {
        Room result;
        try {
            result = roomService.addRoom(room);
            return Response.builder()
                    .success(true)
                    .room(result)
                    .message("Room added successfully")
                    .code(HttpStatus.SC_OK)
                    .build();
        } catch (RoomAlreadyExists e) {
            return Response.builder()
                    .success(false)
                    .message("Room Number already exists")
                    .room(room)
                    .code(HttpStatus.SC_BAD_REQUEST)
                    .build();
        }

    }

    // updateRoom
    @PostMapping("/updateRoom/{id}")
    public Response updateRoomResponse(@RequestBody Room room, @PathVariable int id) {
        return updateRoom(room, id);
    }

    @PutMapping("/updateRoom/{id}")
    public Response updateRoom(@RequestBody Room room, @PathVariable int id) {
        try {
            Room result = roomService.updateRoom(room, id);
            return Response.builder()
                    .success(true)
                    .room(result)
                    .message("Room updated successfully")
                    .code(HttpStatus.SC_OK)
                    .build();
        } catch (RoomNotFoundException e) {

            return Response.builder()
                    .success(false)
                    .room(room)
                    .message(e.getMessage())
                    .code(HttpStatus.SC_BAD_REQUEST)
                    .build();
        }
    }

    // deleteRoom
    @GetMapping("/deleteRoom/{id}")
    public Response deleteRoomResponse(@PathVariable int id) {
        return deleteRoom(id);
    }

    @DeleteMapping("/deleteRoom/{id}")
    public Response deleteRoom(@PathVariable int id) {
        try {
            Room result = roomService.deleteRoom(id);
            return Response.builder()
                    .success(true)
                    .room(result)
                    .message("Room deleted  successfully")
                    .code(HttpStatus.SC_OK)
                    .build();
        } catch (RoomNotFoundException e) {

            return Response.builder()
                    .success(false)
                    .room(null)
                    .message(e.getMessage())
                    .code(HttpStatus.SC_BAD_REQUEST)
                    .build();
        }
    }

    // get available rooms
    @GetMapping("/getAvailableRooms")
    public List<Room> getAvailableRooms() {
        List<Room> rooms = roomService.getAvailableRooms();
        return rooms;
    }

    @PostMapping("/setAvailabilityAndReservationId")
    public Response setAvailabilityAndReservationId(@RequestBody Room room) {
        try {
            Room result = roomService.setAvailabilityAndReservationId(room);
            return Response.builder()
                    .success(true)
                    .room(result)
                    .message("Room availability status updated successfully")
                    .code(HttpStatus.SC_OK)
                    .build();
        } catch (RoomNotFoundException e) {
            return Response.builder()
                    .success(false)
                    .room(null)
                    .message(e.getMessage())
                    .code(HttpStatus.SC_BAD_REQUEST)
                    .build();
        }
    }

    @GetMapping("/getRoomByRoomNumber/{roomNumber}")
    public Response getRoomByRoomNumber(@PathVariable String roomNumber) {
        try {
            Room result = roomService.getRoomByRoomNumber(roomNumber);
            return Response.builder()
                    .success(true)
                    .room(result)
                    .message("Room data fetched successfully")
                    .code(HttpStatus.SC_OK)
                    .build();
        } catch (RoomNotFoundException e) {
            return Response.builder()
                    .success(false)
                    .room(null)
                    .message(e.getMessage())
                    .code(HttpStatus.SC_BAD_REQUEST)
                    .build();
        }
    }

    @PostMapping("/setRoomAvailabillity/{roomNumber}/{availability}")
    public Response setRoomAvailabillity(@PathVariable String roomNumber, @PathVariable boolean availability) {
        try {
            boolean result = roomService.setRoomAvailabillity(roomNumber, availability);
            return Response.builder()
                    .success(result)
                    .room(null)
                    .message("Room is now available successfully")
                    .code(HttpStatus.SC_OK)
                    .build();
        } catch (RoomNotFoundException e) {
            return Response.builder()
                    .success(false)
                    .room(null)
                    .message(e.getMessage())
                    .code(HttpStatus.SC_BAD_REQUEST)
                    .build();
        }
    }

    // get all rooms available and not available
    @GetMapping("/getAllRooms")
    public List<Room> getAllRooms() {
        return roomService.getAllRooms();
    }

}
