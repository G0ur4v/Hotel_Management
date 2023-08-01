package com.service.room.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    private boolean success;
    private RoomService roomService;;
    private String message;
    private int code;
}
