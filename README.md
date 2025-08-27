# 🚀 Microservices with Circuit Breakers + React UI

A complete microservices architecture demonstrating **Resilience4j circuit breakers** with a **React frontend** for testing service resilience.

## 🏗️ **Architecture Overview**

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   React UI      │    │  Order Service  │    │ Payment Service │
│  (Port 5173)   │◄──►│   (Port 8080)   │◄──►│   (Port 8081)   │
│                 │    │                 │    │                 │
│ • Login/Register│    │ • Circuit       │    │ • Admin Toggle  │
│ • Place Orders  │    │   Breakers      │    │ • Payment Logic │
│ • Admin Panel   │    │ • Auth + CORS   │    │                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                │
                                ▼
                       ┌─────────────────┐
                       │ Shipping Service│
                       │   (Port 8082)   │
                       │                 │
                       │ • Admin Toggle  │
                       │ • Shipping Logic│
                       └─────────────────┘
```

## 🎯 **Key Features**

- **🔐 Authentication**: Admin (admin/admin) + User self-registration
- **🔄 Circuit Breakers**: Automatic fallbacks when services fail
- **🎛️ Admin Controls**: Toggle services on/off at runtime
- **📱 Modern UI**: React + Vite frontend
- **🛡️ Security**: Spring Security with role-based access
- **🌐 CORS**: Frontend can communicate with backend

## 🚀 **Quick Start**

### **1. Start All Services**

**Terminal 1 - Payment Service:**
```bash
cd payment-service
mvn spring-boot:run
```

**Terminal 2 - Shipping Service:**
```bash
cd shipping-service
mvn spring-boot:run
```

**Terminal 3 - Order Service:**
```bash
cd order-service
mvn spring-boot:run
```

**Terminal 4 - React UI:**
```bash
cd ui
npm install
npm run dev
```

### **2. Access the Application**

- **Frontend**: http://localhost:5173
- **Order Service**: http://localhost:8080
- **Payment Service**: http://localhost:8081
- **Shipping Service**: http://localhost:8082

## 🧪 **Testing Scenarios**

### **Scenario 1: All Services Running**
1. Login as admin (admin/admin) or register a new user
2. Go to "Place Order" page
3. Enter amount and address
4. Click "Place Order"
5. **Expected**: Real payment + real shipping data

### **Scenario 2: Payment Service Disabled**
1. Login as admin (admin/admin)
2. Go to "Admin Dashboard"
3. Click "Disable" for Payment Service
4. Go back to "Place Order"
5. Place an order
6. **Expected**: Payment fallback + real shipping data

### **Scenario 3: Shipping Service Disabled**
1. In Admin Dashboard, disable Shipping Service
2. Place an order
3. **Expected**: Real payment data + shipping fallback

## 🔧 **Circuit Breaker Configuration**

The circuit breakers are configured to:
- **Open after 2 failures** (minimum-number-of-calls=2)
- **Recover after 5 seconds** (wait-duration-in-open-state=5s)
- **Handle IllegalStateException** when services are disabled

## 📁 **Project Structure**

```
microservicesDA/
├── order-service/          # Orchestrator with circuit breakers
│   ├── src/main/java/
│   │   ├── config/         # Security + CORS config
│   │   ├── controller/     # Order + Auth + Admin controllers
│   │   └── model/          # Request/Response models
│   └── src/main/resources/
│       └── application.properties  # Circuit breaker config
├── payment-service/         # Payment microservice
│   ├── src/main/java/
│   │   ├── controller/     # Payment + Admin controllers
│   │   └── service/        # Service toggle
│   └── src/main/resources/
│       └── application.properties
├── shipping-service/        # Shipping microservice
│   ├── src/main/java/
│   │   ├── controller/     # Shipping + Admin controllers
│   │   └── service/        # Service toggle
│   └── src/main/resources/
│       └── application.properties
└── ui/                     # React frontend
    ├── src/pages/
    │   └── App.jsx         # Main application
    ├── package.json
    └── vite.config.js
```

## 🔐 **Authentication**

- **Admin**: `admin` / `admin` (ROLE_ADMIN)
- **Users**: Self-register through UI (ROLE_USER)
- **Admin-only endpoints**: `/admin/**` (service control)

## 🎛️ **Admin Endpoints**

- **Enable/Disable Payment**: `POST /admin/payment/enable|disable`
- **Enable/Disable Shipping**: `POST /admin/shipping/enable|disable`
- **Service Status**: `GET /admin/payment/status`, `GET /admin/shipping/status`

## 📊 **Monitoring**

- **Circuit Breaker Health**: `GET /actuator/health` (Basic auth required)
- **Circuit Breaker Metrics**: `GET /actuator/metrics/resilience4j.circuitbreaker.calls`

## 🚨 **Troubleshooting**

### **Circuit Breaker Not Working**
1. Ensure services are restarted after configuration changes
2. Check that `@CircuitBreaker` methods are public
3. Verify Resilience4j dependencies are included

### **CORS Issues**
1. Ensure CORS config allows `http://localhost:5173`
2. Check that CORS filter is properly configured

### **Authentication Issues**
1. Verify Spring Security is properly configured
2. Check that admin credentials are correct
3. Ensure endpoints have proper security annotations

## 🎉 **Success Indicators**

- ✅ Circuit breakers open when services are disabled
- ✅ Fallback responses are returned
- ✅ Admin can toggle services on/off
- ✅ Users can place orders with custom amounts/addresses
- ✅ UI shows real-time service status
- ✅ Authentication works for both admin and regular users

## 🔮 **Next Steps**

- Add database persistence for orders
- Implement more sophisticated fallback strategies
- Add metrics and monitoring dashboards
- Implement service discovery and load balancing
- Add more comprehensive error handling

---

**Happy Testing! 🚀** The circuit breakers will now work properly with the UI-driven service toggles!
