package com.service.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.reservation.entities.Guest;

public interface GuestRepository extends JpaRepository<Guest, Integer> {

}
