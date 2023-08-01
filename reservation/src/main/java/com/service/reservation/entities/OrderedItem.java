package com.service.reservation.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderedItem {
    private int id;
    private String name;
    private String description;
    private int quantity;
    private double price;

}
