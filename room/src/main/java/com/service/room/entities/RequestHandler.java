package com.service.room.entities;

import java.util.List;

import lombok.Data;

@Data
public class RequestHandler {

    private RoomService roomService;
    private List<OrderedItem> orderedItems;
}
