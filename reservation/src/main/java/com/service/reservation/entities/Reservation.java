package com.service.reservation.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int numberOfChildren;
    private int numberOfAdults;
    private Date checkInDate;
    private Date checkOutDate;
    private int numberOfNights;
    private String roomNumber;
    private String roomType;
    @Builder.Default
    private boolean billed = false;
    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Guest> guests = new ArrayList<>();

}
