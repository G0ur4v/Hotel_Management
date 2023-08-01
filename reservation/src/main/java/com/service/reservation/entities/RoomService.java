package com.service.reservation.entities;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomService {
    private int id;
    private String guestName;
    private String roomNumber;
    @Builder.Default
    private List<OrderedItem> orderedItems = new ArrayList<>();
    private boolean completed;
}
