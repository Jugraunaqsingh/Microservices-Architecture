package com.example.shipping.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@RestController
public class ShippingController {

    @GetMapping("/shipping")
    public Map<String, Object> getShipping() {
        return Map.of(
                "status", "SHIPPED",
                "trackingId", UUID.randomUUID().toString(),
                "expectedDeliveryDate", LocalDate.now().plusDays(3).toString()
        );
    }
}
