package com.service.inventory.controllers;

import java.util.List;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.inventory.entities.Item;
import com.service.inventory.entities.Response;
import com.service.inventory.exception.ItemNotFoundException;
import com.service.inventory.services.ItemService;

@RestController
@RequestMapping("/inventory")
public class ItemController {

    @Autowired
    private ItemService itemService;

    // add item
    @PostMapping("/addItem")
    public Response addItem(@RequestBody Item item) {
        Item result = itemService.addItem(item);
        if (result != null) {
            return Response.builder()
                    .success(true)
                    .item(result)
                    .message("Item added successfully")
                    .code(HttpStatus.SC_OK)
                    .build();
        } else {
            return Response.builder()
                    .success(false)
                    .message("Item not added successfully")
                    .item(item)
                    .code(HttpStatus.SC_BAD_REQUEST)
                    .build();
        }
    }

    // update item
    @PostMapping("/updateItem/{id}")
    public Response updateItemResponse(@RequestBody Item item, @PathVariable int id) {
        return updateItem(item, id);
    }

    @PutMapping("/updateItem/{id}")
    public Response updateItem(@RequestBody Item item, @PathVariable int id) {
        try {
            Item result = itemService.updateItem(item, id);
            return Response.builder()
                    .success(true)
                    .item(result)
                    .message("Item updated successfully")
                    .code(HttpStatus.SC_OK)
                    .build();
        } catch (ItemNotFoundException e) {

            return Response.builder()
                    .success(false)
                    .item(item)
                    .message(e.getMessage())
                    .code(HttpStatus.SC_BAD_REQUEST)
                    .build();
        }
    }

    // delete item
    @GetMapping("/deleteItem/{id}")
    public Response deleteItemResponse(@PathVariable int id) {
        return deleteItem(id);
    }

    @DeleteMapping("/deleteItem/{id}")
    public Response deleteItem(@PathVariable int id) {
        try {
            Item item = itemService.deleteItem(id);
            return Response.builder()
                    .success(true)
                    .item(item)
                    .message("Item deleted  successfully")
                    .code(HttpStatus.SC_OK)
                    .build();
        } catch (ItemNotFoundException e) {

            return Response.builder()
                    .success(false)
                    .item(null)
                    .message(e.getMessage())
                    .code(HttpStatus.SC_BAD_REQUEST)
                    .build();
        }
    }

    // view all items
    @GetMapping("/getItems")
    public List<Item> getItems() {
        return itemService.getItems();
    }

    // get item by id
    @GetMapping("/getItemById/{id}")
    public Item getItemById(@PathVariable int id) {
        return itemService.getItemById(id);
    }

}
