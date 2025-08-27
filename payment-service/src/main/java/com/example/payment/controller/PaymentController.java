package com.example.payment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@RestController
public class PaymentController {

    @GetMapping("/payment")
    public Map<String, Object> getPayment() {
        return Map.of(
                "status", "SUCCESS",
                "transactionId", UUID.randomUUID().toString(),
                "amount", 399.99,
                "timestamp", Instant.now().toString()
        );
    }
}
