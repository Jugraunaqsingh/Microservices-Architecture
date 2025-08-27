package com.example.order.model;

public class PaymentResponse {
    private String status;
    private String transactionId;
    private Double amount;
    private String timestamp;
    private String message; // used by fallback

    public PaymentResponse() {}

    public PaymentResponse(String status, String transactionId, Double amount, String timestamp, String message) {
        this.status = status;
        this.transactionId = transactionId;
        this.amount = amount;
        this.timestamp = timestamp;
        this.message = message;
    }

    public String getStatus() { return status; }
    public String getTransactionId() { return transactionId; }
    public Double getAmount() { return amount; }
    public String getTimestamp() { return timestamp; }
    public String getMessage() { return message; }

    public void setStatus(String status) { this.status = status; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public void setAmount(Double amount) { this.amount = amount; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    public void setMessage(String message) { this.message = message; }
}
