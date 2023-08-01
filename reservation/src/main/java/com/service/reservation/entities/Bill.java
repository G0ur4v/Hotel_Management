package com.service.reservation.entities;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int billNumber;
    private String guestName;
    private String roomNumber;
    private String phoneNumber;
    private Date billDate;
    private double totalAmount;
    private double taxes;
    private double netAmount;
    private boolean checkOutCompleted;
    @Builder.Default
    private boolean paid = false;

}
