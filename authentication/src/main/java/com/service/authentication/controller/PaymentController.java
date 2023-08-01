package com.service.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_RECEPTION')")
public class PaymentController {

    @Autowired
    RestTemplate restTemplate;

    private static final String PAYMENT_BASE_URL = "http://localhost:8006";

    @GetMapping("/payment/{amount}")
    public String Payment(@PathVariable int amount) throws Exception {
        String order = restTemplate.getForObject(PAYMENT_BASE_URL + "/payment/{amount}", String.class,
                amount);
        System.out.println("auth pay controller ----------" + order);
        return order;
    }
}
