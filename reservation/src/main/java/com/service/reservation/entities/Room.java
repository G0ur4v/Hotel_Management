package com.service.reservation.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    private int id;
    private String roomNumber;
    private String roomType;
    private double price;
    private boolean available;
    private int reservationId;
}
