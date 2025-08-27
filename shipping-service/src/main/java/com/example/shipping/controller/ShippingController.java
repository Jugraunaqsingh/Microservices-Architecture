package com.example.shipping.controller;

import com.example.shipping.service.ServiceToggle;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@RestController
public class ShippingController {
    private final ServiceToggle toggle;
    public ShippingController(ServiceToggle toggle) { this.toggle = toggle; }

    // Accept optional address; throws when disabled to trigger CB.
    @GetMapping("/shipping")
    public Map<String, Object> getShipping(@RequestParam(name="address", required=false) String address) {
        if (!toggle.isEnabled()) {
            throw new IllegalStateException("SHIPPING_SERVICE_DISABLED");
        }
        return Map.of(
                "status", "SHIPPED",
                "trackingId", UUID.randomUUID().toString(),
                "expectedDeliveryDate", LocalDate.now().plusDays(3).toString(),
                "address", address == null ? "N/A" : address
        );
    }
}
