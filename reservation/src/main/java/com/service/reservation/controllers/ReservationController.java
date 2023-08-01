package com.service.reservation.controllers;

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

import com.service.reservation.entities.Reservation;
import com.service.reservation.entities.Response;
import com.service.reservation.entities.Room;
import com.service.reservation.exception.ReservationNotFoundException;
import com.service.reservation.exception.RoomTypeNotAvailable;
import com.service.reservation.services.ReservationService;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/getAvailableRooms")
    public List<Room> getAvailableRooms() {
        return reservationService.getAvailableRooms();
    }

    @PostMapping("/addReservation")
    public Response addReservation(@RequestBody Reservation reservation) {
        Reservation result;
        try {
            result = reservationService.addReservation(reservation);
            if (result != null) {
                return Response.builder()
                        .success(true)
                        .reservation(result)
                        .message("Reservation added successfully")
                        .code(HttpStatus.SC_OK)
                        .build();
            } else {
                return Response.builder()
                        .success(false)
                        .message("Reservation not added successfully/Room are not avaialable")
                        .reservation(null)
                        .code(HttpStatus.SC_BAD_REQUEST)
                        .build();
            }
        } catch (RoomTypeNotAvailable e) {
            return Response.builder()
                    .success(false)
                    .message(e.getMessage())
                    .reservation(null)
                    .code(HttpStatus.SC_BAD_REQUEST)
                    .build();

        }

    }

    // Updating Reservation details
    @PostMapping("/updateReservation/{reservationId}")
    public Response updateReservationResponse(@RequestBody Reservation reservation, @PathVariable int reservationId) {
        return updateReservation(reservation, reservationId);
    }

    @PutMapping("/updateReservation/{reservationId}")
    public Response updateReservation(@RequestBody Reservation reservation, @PathVariable int reservationId) {
        try {
            Reservation result = reservationService.updatReservation(reservation, reservationId);
            return Response.builder()
                    .success(true)
                    .reservation(result)
                    .message("Reservation updated successfully")
                    .code(HttpStatus.SC_OK)
                    .build();
        } catch (ReservationNotFoundException e) {
            return Response.builder()
                    .success(false)
                    .message("Reservation not updated successfully")
                    .reservation(null)
                    .code(HttpStatus.SC_BAD_REQUEST)
                    .build();
        }
    }

    // Cancel Reservation
    @GetMapping("/deleteReservation/{reservationId}")
    public Response deleteReservationResponse(@PathVariable int reservationId) {
        return deleteReservation(reservationId);
    }

    @DeleteMapping("/deleteReservation/{reservationId}")
    public Response deleteReservation(@PathVariable int reservationId) {
        try {
            Reservation result = reservationService.deleteReservation(reservationId);
            return Response.builder()
                    .success(true)
                    .reservation(result)
                    .message("Reservation deleted successfully")
                    .code(HttpStatus.SC_OK)
                    .build();
        } catch (ReservationNotFoundException e) {
            return Response.builder()
                    .success(false)
                    .message("Reservation not deleted successfully")
                    .reservation(null)
                    .code(HttpStatus.SC_BAD_REQUEST)
                    .build();
        }
    }

    // get Reservation by id
    @GetMapping("/getReservationById/{reservationId}")
    public Response getReservationById(@PathVariable int reservationId) {
        System.out.println("-----------reservationId " + reservationId);
        Reservation result = reservationService.getReservationById(reservationId);
        if (result != null) {
            return Response.builder()
                    .success(true)
                    .reservation(result)
                    .message("Reservation fetched with id " + reservationId)
                    .code(HttpStatus.SC_OK)
                    .build();
        } else {
            return Response.builder()
                    .success(false)
                    .message("Reservation not found with id " + reservationId)
                    .reservation(null)
                    .code(HttpStatus.SC_BAD_REQUEST)
                    .build();
        }

    }

    @GetMapping("/getActiveReservations")
    public List<Reservation> getActiveReservations() {
        return reservationService.getActiveReservations();
    }

    @DeleteMapping("/deleteGuest/{guestId}")
    public void deleteGuest(@PathVariable int guestId) {
        reservationService.deleteGuestById(guestId);
    }

}
