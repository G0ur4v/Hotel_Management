package com.service.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.inventory.entities.Room;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    List<Room> findByAvailable(boolean available);

    Room findByRoomNumber(String roomNumber);

}
