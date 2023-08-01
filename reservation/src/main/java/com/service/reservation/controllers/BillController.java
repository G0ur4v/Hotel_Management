package com.service.reservation.controllers;

import java.util.List;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.reservation.entities.Bill;
import com.service.reservation.entities.Report;
import com.service.reservation.entities.Response;
import com.service.reservation.exception.ReservationNotFoundException;
import com.service.reservation.services.BillService;

@RestController
@RequestMapping("/reservation")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping("/generateBill")
    public Response generateBill(@RequestBody Bill bill) {
        try {
            Bill result = billService.generateBill(bill);
            return Response.builder()
                    .success(true)
                    .bill(result)
                    .message("Bill generated successfully")
                    .code(HttpStatus.SC_OK)
                    .build();
        } catch (ReservationNotFoundException e) {
            return Response.builder()
                    .success(false)
                    .message(e.getMessage())
                    .reservation(null)
                    .code(HttpStatus.SC_BAD_REQUEST)
                    .build();
        }
    }

    @GetMapping("/getNotPaidBills")
    public List<Bill> getNotPaidBills() {
        List<Bill> result = billService.getNotPaidBills();
        return result;
    }

    @PostMapping("/setPaidForBill/{billId}")
    public Response setPaidForBill(@PathVariable int billId) {
        Bill result = billService.setPaidForBill(billId);
        if (result != null) {
            return Response.builder()
                    .success(true)
                    .bill(result)
                    .message("Bill Paid successfully")
                    .code(HttpStatus.SC_OK)
                    .build();
        } else {
            return Response.builder()
                    .success(false)
                    .message("Bill not paid successfully")
                    .bill(null)
                    .code(HttpStatus.SC_BAD_REQUEST)
                    .build();
        }
    }

    // generate report last 30 days
    @GetMapping("/report/{days}")
    public Report generateReport(@PathVariable int days) {
        return billService.generateReport(days);
    }

}
