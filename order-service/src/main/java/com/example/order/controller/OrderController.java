package com.example.order.controller;

import com.example.order.model.OrderAggregateResponse;
import com.example.order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.UUID;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // ---- Aggregate endpoint ----

    @GetMapping("/place-order")
    public OrderAggregateResponse placeOrder() {
        var payment = orderService.fetchPayment();
        var shipping = orderService.fetchShipping();

        return new OrderAggregateResponse(
                UUID.randomUUID().toString(),
                Instant.now().toString(),
                payment,
                shipping);
    }
}
