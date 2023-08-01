package com.service.room.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.room.entities.OrderedItem;

public interface OrderedItemRepository extends JpaRepository<OrderedItem, Integer> {

}
