package com.example.order.controller;

import com.example.order.model.OrderAggregateResponse;
import com.example.order.model.PaymentResponse;
import com.example.order.model.ShippingResponse;
import com.example.order.model.PlaceOrderRequest;
import com.example.order.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Existing GET stays (no body)
    @GetMapping("/place-order")
    public OrderAggregateResponse placeOrder() {
        PaymentResponse payment = orderService.fetchPayment(null);
        ShippingResponse shipping = orderService.fetchShipping(null);
        return new OrderAggregateResponse(
                UUID.randomUUID().toString(),
                Instant.now().toString(),
                payment, shipping);
    }

    // New POST accepts amount + address from UI
    @PostMapping("/place-order")
    public OrderAggregateResponse placeOrder(@RequestBody PlaceOrderRequest req) {
        PaymentResponse payment = orderService.fetchPayment(req.getAmount());
        ShippingResponse shipping = orderService.fetchShipping(req.getAddress());
        return new OrderAggregateResponse(
                UUID.randomUUID().toString(),
                Instant.now().toString(),
                payment, shipping);
    }
}
