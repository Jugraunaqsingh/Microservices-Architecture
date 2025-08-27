# Multi-Service Spring Boot Workspace with Resilience4j Circuit Breakers

This workspace contains three independent Spring Boot microservices demonstrating Resilience4j circuit breaker patterns:

## Services Overview

- **payment-service** (Port 8081) - Handles payment processing
- **shipping-service** (Port 8082) - Manages shipping information  
- **order-service** (Port 8080) - Orchestrates orders using both services with circuit breakers

## Architecture

The `order-service` calls both `payment-service` and `shipping-service` over REST using RestTemplate and implements **two separate circuit breakers**:

- `paymentServiceCircuitBreaker` → fallback when PaymentService is down
- `shippingServiceCircuitBreaker` → fallback when ShippingService is down

Each circuit breaker has its own configuration and fallback method.

## Prerequisites

- Java 17
- Maven 3.6+
- Spring Boot 3.3.2

## Build & Run Instructions

### 1. Payment Service
```bash
cd payment-service
mvn spring-boot:run
```
**Verify**: http://localhost:8081/payment

### 2. Shipping Service  
```bash
cd shipping-service
mvn spring-boot:run
```
**Verify**: http://localhost:8082/shipping

### 3. Order Service
```bash
cd order-service
mvn spring-boot:run
```
**Verify**: http://localhost:8080/actuator/health

## Testing Scenarios

### Case 1: All Services UP
- **GET** http://localhost:8080/place-order
- **Expected**: JSON with real payment details (status=SUCCESS, transactionId, amount, timestamp) and real shipping details (status=SHIPPED, trackingId, expectedDeliveryDate)

### Case 2: Stop PaymentService (Ctrl+C in its terminal)
- **GET** http://localhost:8080/place-order  
- **Expected**: Payment shows fallback with status="PAYMENT_SERVICE_UNAVAILABLE" and fallback message, but shipping remains real details

### Case 3: Stop ShippingService (Ctrl+C in its terminal)
- **GET** http://localhost:8080/place-order
- **Expected**: Shipping shows fallback with status="SHIPPING_SERVICE_UNAVAILABLE" and fallback message, but payment remains real details

## Circuit Breaker Monitoring

- **Health Check**: http://localhost:8080/actuator/health
- **Circuit Breakers**: http://localhost:8080/actuator/circuitbreakers
- **Metrics**: http://localhost:8080/actuator/metrics

## Circuit Breaker Configuration

Each circuit breaker has independent settings:

- **paymentServiceCircuitBreaker**: 6-call sliding window, 50% failure threshold, 10s open state
- **shippingServiceCircuitBreaker**: 8-call sliding window, 50% failure threshold, 8s open state

## Project Structure

```
microservicesDA/
├── README.md
├── payment-service/
│   ├── pom.xml
│   └── src/main/java/com/example/payment/
│       ├── PaymentServiceApplication.java
│       └── controller/PaymentController.java
├── shipping-service/
│   ├── pom.xml
│   └── src/main/java/com/example/shipping/
│       ├── ShippingServiceApplication.java
│       └── controller/ShippingController.java
└── order-service/
    ├── pom.xml
    └── src/main/java/com/example/order/
        ├── OrderServiceApplication.java
        ├── controller/OrderController.java
        └── model/
            ├── PaymentResponse.java
            ├── ShippingResponse.java
            └── OrderAggregateResponse.java
```
