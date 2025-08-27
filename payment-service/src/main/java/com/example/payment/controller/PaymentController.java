package com.example.payment.controller;

import com.example.payment.service.ServiceToggle;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@RestController
public class PaymentController {
    private final ServiceToggle toggle;

    public PaymentController(ServiceToggle toggle) {
        this.toggle = toggle;
    }

    // Accept optional amount as query param; throws when disabled to trigger CB.
    @GetMapping("/payment")
    public Map<String, Object> getPayment(@RequestParam(name = "amount", required = false) Double amount) {
        if (!toggle.isEnabled()) {
            throw new IllegalStateException("PAYMENT_SERVICE_DISABLED");
        }
        return Map.of(
                "status", "SUCCESS",
                "transactionId", UUID.randomUUID().toString(),
                "amount", amount == null ? 399.99 : amount,
                "timestamp", Instant.now().toString());
    }
}
