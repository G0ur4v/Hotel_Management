package com.service.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.inventory.entities.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {

}
