package com.example.order.model;

public class PlaceOrderRequest {
    private Double amount;
    private String address;

    public Double getAmount() { return amount; }
    public String getAddress() { return address; }
    public void setAmount(Double amount) { this.amount = amount; }
    public void setAddress(String address) { this.address = address; }
}
