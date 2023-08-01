package com.service.reservation.entities;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    @Builder.Default
    private boolean success = false;
    private Reservation reservation;
    private Bill bill;
    @Builder.Default
    private String message = null;
    private Room room;
    private RoomService roomService;
    private List<Bill> bills;
    private int code;
}
