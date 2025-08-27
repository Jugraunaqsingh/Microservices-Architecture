package com.example.order.service;

import com.example.order.model.PaymentResponse;
import com.example.order.model.ShippingResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;

@Service
public class OrderService {

    private final RestTemplate restTemplate;

    @Value("${service.payment.url}")
    private String paymentUrl;

    @Value("${service.shipping.url}")
    private String shippingUrl;

    public OrderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // ---- Remote calls wrapped with SEPARATE circuit breakers ----

    @CircuitBreaker(name = "paymentServiceCircuitBreaker", fallbackMethod = "paymentFallback")
    public PaymentResponse fetchPayment(Double amount) {
        String url = paymentUrl + (amount != null ? ("?amount=" + amount) : "");
        ResponseEntity<PaymentResponse> response =
                restTemplate.getForEntity(url, PaymentResponse.class);
        return response.getBody();
    }

    @CircuitBreaker(name = "shippingServiceCircuitBreaker", fallbackMethod = "shippingFallback")
    public ShippingResponse fetchShipping(String address) {
        String url = shippingUrl + (address != null ? ("?address=" + address) : "");
        ResponseEntity<ShippingResponse> response =
                restTemplate.getForEntity(url, ShippingResponse.class);
        return response.getBody();
    }

    // ---- Fallbacks (distinct), MUST match return type and accept Throwable ----

    public PaymentResponse paymentFallback(Double amount, Throwable t) {
        PaymentResponse pr = new PaymentResponse();
        pr.setStatus("PAYMENT_SERVICE_UNAVAILABLE");
        pr.setMessage("Payment fallback: " + t.getClass().getSimpleName());
        pr.setTimestamp(Instant.now().toString());
        return pr;
    }

    public ShippingResponse shippingFallback(String address, Throwable t) {
        ShippingResponse sr = new ShippingResponse();
        sr.setStatus("SHIPPING_SERVICE_UNAVAILABLE");
        sr.setMessage("Shipping fallback: " + t.getClass().getSimpleName());
        return sr;
    }
}
