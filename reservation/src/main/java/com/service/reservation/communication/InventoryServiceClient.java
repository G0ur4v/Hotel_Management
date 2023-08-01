package com.service.reservation.communication;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.service.reservation.entities.Response;
import com.service.reservation.entities.Room;

@FeignClient(name = "inventory-service")
public interface InventoryServiceClient {

    @GetMapping("/inventory/getAvailableRooms")
    List<Room> getAvailableRooms();

    @GetMapping("/inventory/getRoomByRoomNumber/{roomNumber}")
    Response getRoomByRoomNumber(@PathVariable String roomNumber);

    @PostMapping("/inventory/setAvailabilityAndReservationId")
    public Response setAvailabilityAndReservationId(@RequestBody Room room);

    @PostMapping("/inventory/setRoomAvailabillity/{roomNumber}/{availability}")
    public Response setRoomAvailabillity(@PathVariable String roomNumber, @PathVariable boolean availability);
}
