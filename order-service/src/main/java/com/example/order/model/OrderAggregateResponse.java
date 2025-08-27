package com.example.order.model;

public class OrderAggregateResponse {
    private String orderId;
    private String timestamp;
    private PaymentResponse payment;
    private ShippingResponse shipping;

    public OrderAggregateResponse() {}

    public OrderAggregateResponse(String orderId, String timestamp, PaymentResponse payment, ShippingResponse shipping) {
        this.orderId = orderId;
        this.timestamp = timestamp;
        this.payment = payment;
        this.shipping = shipping;
    }

    public String getOrderId() { return orderId; }
    public String getTimestamp() { return timestamp; }
    public PaymentResponse getPayment() { return payment; }
    public ShippingResponse getShipping() { return shipping; }

    public void setOrderId(String orderId) { this.orderId = orderId; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public void setPayment(PaymentResponse payment) { this.payment = payment; }
    public void setShipping(ShippingResponse shipping) { this.shipping = shipping; }
}
