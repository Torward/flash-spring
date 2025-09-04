# Code Service

Referral Code Service for Flash Backend - handles referral code functionality for the Myfriend app.

## Features

- Create and manage referral codes
- Track usage and expiration
- Validate referral codes
- Reward system integration
- RESTful API endpoints

## API Endpoints

### Referral Code Management

- `POST /api/referral-codes` - Create a new referral code
- `GET /api/referral-codes/{codeId}` - Get referral code by ID
- `GET /api/referral-codes/code/{codeValue}` - Get referral code by value
- `GET /api/referral-codes/user/{userId}` - Get user's referral codes
- `GET /api/referral-codes/status/{status}` - Get codes by status
- `GET /api/referral-codes` - Get all referral codes
- `GET /api/referral-codes/active` - Get active referral codes
- `PUT /api/referral-codes/{codeId}` - Update referral code
- `DELETE /api/referral-codes/{codeId}` - Delete referral code

### Referral Code Usage

- `POST /api/referral-codes/{codeValue}/use` - Use a referral code
- `POST /api/referral-codes/{codeId}/expire` - Expire a referral code
- `GET /api/referral-codes/validate/{codeValue}` - Validate referral code

## Data Model

### ReferralCode Entity

- `codeId`: Unique identifier (UUID)
- `codeValue`: Unique referral code string
- `userId`: Owner of the referral code
- `rewardAmount`: Reward amount for using the code
- `maxUsage`: Maximum number of uses
- `currentUsage`: Current usage count
- `status`: ACTIVE, EXPIRED, DISABLED
- `expiresAt`: Expiration date/time
- `createdAt`: Creation timestamp
- `updatedAt`: Last update timestamp

## Configuration

The service runs on port 8084 and connects to:
- PostgreSQL database
- Eureka service discovery
- Other flash-backend microservices

## Building and Running

### Prerequisites

- Java 17
- Maven
- PostgreSQL
- Eureka Server

### Build

```bash
mvn clean package
```

### Run

```bash
java -jar target/code-service-0.0.1-SNAPSHOT.jar
```

### Docker

```bash
docker build -t code-service .
docker run -p 8084:8084 code-service
```

## Dependencies

- Spring Boot 3.2.0
- Spring Data JPA
- PostgreSQL
- Eureka Client
- Lombok
- Validation
