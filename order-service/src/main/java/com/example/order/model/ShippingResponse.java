package com.example.order.model;

public class ShippingResponse {
    private String status;
    private String trackingId;
    private String expectedDeliveryDate;
    private String message; // used by fallback

    public ShippingResponse() {}

    public ShippingResponse(String status, String trackingId, String expectedDeliveryDate, String message) {
        this.status = status;
        this.trackingId = trackingId;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.message = message;
    }

    public String getStatus() { return status; }
    public String getTrackingId() { return trackingId; }
    public String getExpectedDeliveryDate() { return expectedDeliveryDate; }
    public String getMessage() { return message; }

    public void setStatus(String status) { this.status = status; }
    public void setTrackingId(String trackingId) { this.trackingId = trackingId; }
    public void setExpectedDeliveryDate(String expectedDeliveryDate) { this.expectedDeliveryDate = expectedDeliveryDate; }
    public void setMessage(String message) { this.message = message; }
}
