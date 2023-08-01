package com.service.authentication.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.service.authentication.entities.Response;

@RestController
@RequestMapping("/reservation")
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_RECEPTION')")
public class ReservationController {

    @Autowired
    RestTemplate restTemplate;

    private static final String RESERVATION_BASE_URL = "http://localhost:8000/reservation";

    // RESERVATION CONTROLLER

    @GetMapping("/getAvailableRooms")
    public List<Object> getAvailableRooms() {
        Object[] rooms = restTemplate.getForObject(RESERVATION_BASE_URL + "/getAvailableRooms", Object[].class);
        return Arrays.asList(rooms);
    }

    @GetMapping("/getReservationById/{reservationId}")
    public Response getReservationById(@PathVariable int reservationId) {
        return restTemplate.getForObject(RESERVATION_BASE_URL + "/getReservationById/{reservationId}", Response.class,
                reservationId);
    }

    @GetMapping("/getActiveReservations")
    public List<Object> getActiveReservations() {
        Object[] rooms = restTemplate.getForObject(RESERVATION_BASE_URL + "/getActiveReservations", Object[].class);
        return Arrays.asList(rooms);

    }

    @PostMapping("/addReservation")
    public Response addReservation(@RequestBody Object reservation) {
        return restTemplate.postForObject(RESERVATION_BASE_URL + "/addReservation", reservation, Response.class);

    }

    @PutMapping("/updateReservation/{reservationId}")
    public Response updateReservation(@RequestBody Object reservation, @PathVariable int reservationId) {
        return restTemplate.postForObject(RESERVATION_BASE_URL + "/updateReservation/{reservationId}", reservation,
                Response.class, reservationId);

    }

    @DeleteMapping("/deleteReservation/{reservationId}")
    public Response deleteReservation(@PathVariable int reservationId) {
        return restTemplate.getForObject(RESERVATION_BASE_URL + "/deleteReservation/{reservationId}", Response.class,
                reservationId);

    }

    // BILL CONTROLLER
    @PostMapping("/generateBill")
    public Response generateBill(@RequestBody Object bill) {
        return restTemplate.postForObject(RESERVATION_BASE_URL + "/generateBill", bill, Response.class);

    }

    @GetMapping("/getNotPaidBills")
    public List<Object> getNotPaidBills() {
        Object[] bills = restTemplate.getForObject(RESERVATION_BASE_URL + "/getNotPaidBills", Object[].class);
        return Arrays.asList(bills);
    }

    @PostMapping("/setPaidForBill/{billId}")
    public Response setPaidForBill(@PathVariable int billId) {
        return restTemplate.postForObject(RESERVATION_BASE_URL + "/setPaidForBill/{billId}", null, Response.class,
                billId);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/report/{days}")
    public Object getReport(@PathVariable int days) {
        return restTemplate.getForObject(RESERVATION_BASE_URL + "/report/{days}", Object.class,
                days);

    }

    // Guest Controller
    @DeleteMapping("/deleteGuest/{guestId}")
    public void deleteGuest(@PathVariable int guestId) {
        restTemplate.delete(RESERVATION_BASE_URL + "/deleteGuest/{guestId}",
                guestId);
    }

}
