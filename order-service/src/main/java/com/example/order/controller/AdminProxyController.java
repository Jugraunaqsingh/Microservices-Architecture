package com.example.order.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminProxyController {
    private final RestTemplate rest;
    public AdminProxyController(RestTemplate rest) { this.rest = rest; }

    @Value("${service.payment.adminBase:http://localhost:8081/admin}")
    private String paymentAdminBase;

    @Value("${service.shipping.adminBase:http://localhost:8082/admin}")
    private String shippingAdminBase;

    @PostMapping("/payment/enable")
    public ResponseEntity<?> enablePayment() {
        return rest.postForEntity(paymentAdminBase + "/enable", null, Map.class);
    }

    @PostMapping("/payment/disable")
    public ResponseEntity<?> disablePayment() {
        return rest.postForEntity(paymentAdminBase + "/disable", null, Map.class);
    }

    @GetMapping("/payment/status")
    public Map<?,?> paymentStatus() {
        return rest.getForObject(paymentAdminBase + "/status", Map.class);
    }

    @PostMapping("/shipping/enable")
    public ResponseEntity<?> enableShipping() {
        return rest.postForEntity(shippingAdminBase + "/enable", null, Map.class);
    }

    @PostMapping("/shipping/disable")
    public ResponseEntity<?> disableShipping() {
        return rest.postForEntity(shippingAdminBase + "/disable", null, Map.class);
    }

    @GetMapping("/shipping/status")
    public Map<?,?> shippingStatus() {
        return rest.getForObject(shippingAdminBase + "/status", Map.class);
    }
}
