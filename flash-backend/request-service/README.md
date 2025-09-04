# Request Service

## Overview
The Request Service is a microservice responsible for handling withdrawal requests in the Myfriend backend system. It provides functionality for users to request withdrawals from their balance and for administrators to approve or reject these requests.

## Features
- Create withdrawal requests
- View request history by user
- Admin approval/rejection of requests
- Request status tracking
- Transaction ID management
- Fee calculation and tracking

## API Endpoints

### User Endpoints

#### Create Withdrawal Request
```http
POST /api/requests
Content-Type: application/json

{
  "userId": "user123",
  "amount": 100.50,
  "paymentMethod": "bank_transfer",
  "accountDetails": "Account: 1234567890, Bank: ABC Bank",
  "currency": "USD"
}
```

#### Get User's Requests
```http
GET /api/requests/user/{userId}
```

#### Get Request by ID
```http
GET /api/requests/{requestId}
```

### Admin Endpoints

#### Get All Requests
```http
GET /api/requests
```

#### Get Pending Requests
```http
GET /api/requests/pending
```

#### Get Requests by Status
```http
GET /api/requests/status/{status}
```

#### Approve Request
```http
PUT /api/requests/{requestId}/approve
Content-Type: application/json

{
  "processedBy": "admin123",
  "transactionId": "TXN123456",
  "feeAmount": 2.50
}
```

#### Reject Request
```http
PUT /api/requests/{requestId}/reject
Content-Type: application/json

{
  "processedBy": "admin123",
  "rejectionReason": "Insufficient balance"
}
```

#### Update Request
```http
PUT /api/requests/{requestId}
Content-Type: application/json

{
  "status": "APPROVED",
  "processedBy": "admin123",
  "transactionId": "TXN123456",
  "feeAmount": 2.50
}
```

#### Delete Request
```http
DELETE /api/requests/{requestId}
```

## Request Status Flow
```
PENDING → APPROVED | REJECTED
```

## Data Model

### Request Entity
- `requestId`: String (UUID) - Primary Key
- `userId`: String - User who made the request
- `amount`: BigDecimal - Requested amount
- `status`: String - PENDING/APPROVED/REJECTED
- `paymentMethod`: String - Payment method
- `accountDetails`: String - Account information
- `currency`: String - Currency code
- `createdAt`: LocalDateTime - Creation timestamp
- `updatedAt`: LocalDateTime - Last update timestamp
- `processedAt`: LocalDateTime - Processing timestamp
- `processedBy`: String - Admin who processed the request
- `rejectionReason`: String - Reason for rejection
- `transactionId`: String - Transaction ID after approval
- `feeAmount`: BigDecimal - Processing fee
- `netAmount`: BigDecimal - Amount after fee deduction

## Configuration

### Application Properties
```yaml
server:
  port: 8085

spring:
  application:
    name: request-service
  datasource:
    url: jdbc:postgresql://localhost:5432/request_service_db
    username: postgres
    password: password
  jpa:
    hibernate:
      ddl-auto: update

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka
```

## Dependencies
- Spring Boot 3.1.0
- Spring Data JPA
- PostgreSQL
- Eureka Client
- Lombok
- Validation

## Running the Service

### Local Development
```bash
mvn spring-boot:run
```

### Docker
```bash
docker build -t request-service .
docker run -p 8085:8085 request-service
```

### Docker Compose
```yaml
version: '3.8'
services:
  request-service:
    build: .
    ports:
      - "8085:8085"
    environment:
      - SPRING_PROFILES_ACTIVE=docker
    depends_on:
      - postgres
      - eureka-server

  postgres:
    image: postgres:14
    environment:
      POSTGRES_DB: request_service_db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: password
```

## Health Checks
- Health endpoint: `GET /actuator/health`
- Metrics endpoint: `GET /actuator/metrics`
- Info endpoint: `GET /actuator/info`

## Error Handling
- `RequestNotFoundException`: Thrown when request is not found
- Validation errors: Returned for invalid input data
- Database errors: Handled with appropriate HTTP status codes

## Security Considerations
- Input validation using Bean Validation
- SQL injection prevention through JPA
- Authentication should be implemented at API Gateway level
- Authorization checks for admin operations

## Future Enhancements
- Email notifications for request status changes
- Webhook integration for payment processors
- Batch processing for multiple requests
- Advanced reporting and analytics
- Integration with external payment gateways
