package com.service.reservation.communication;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.service.reservation.entities.Response;

@FeignClient(name = "room-service")
public interface RoomServiceClient {

    @GetMapping("/room/getRoomService/{roomNumber}/{completed}")
    public Response getRoomServiceByRoomNumberAndCompleted(@PathVariable String roomNumber,
            @PathVariable boolean completed);

    @PostMapping("/room/setRoomServiceCompleted/{roomNumber}/{completed}")
    public Response setRoomServiceCompleted(@PathVariable String roomNumber, @PathVariable boolean completed);
}
