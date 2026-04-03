# Flash-Spring Backend Enhancement Guide

## 📋 Overview

This document describes the implementation of enterprise-grade enhancements for the Flash-Spring microservices architecture.

### Implemented Features

1. ✅ **Spring Cloud Config Server** - Centralized configuration management
2. ✅ **SpringDoc OpenAPI** - API documentation with Swagger UI
3. ✅ **Integration Tests** - Testcontainers-based testing framework
4. ✅ **CI/CD Pipeline** - GitHub Actions workflow
5. ✅ **Distributed Tracing** - Micrometer Tracing with Zipkin
6. ✅ **Circuit Breaker** - Resilience4j fault tolerance
7. ✅ **Message Broker** - RabbitMQ for async communication

---

## 1. Spring Cloud Config Server

### Location
`/flash-backend/config-server/`

### Purpose
Centralized configuration management for all microservices using Git or native file storage.

### Key Features
- Externalized configuration
- Profile-specific settings (dev, test, prod)
- Dynamic refresh support
- Encryption support for sensitive data

### Configuration Files Created
- `application.yml` - Config server settings
- `configs/application.yml` - Common settings for all services
- `configs/auth-service.yml` - Auth service configuration
- `configs/user-service.yml` - User service configuration
- `configs/api-gateway.yml` - Gateway routes and filters
- `configs/eureka-client.yml` - Service discovery settings
- `configs/database-template.yml` - Database connection template
- `configs/rabbitmq.yml` - Message broker settings
- `configs/resilience4j.yml` - Circuit breaker configuration
- `configs/tracing.yml` - Distributed tracing setup

### Usage
```yaml
# In your microservice application.yml
spring:
  config:
    import: optional:configserver:http://localhost:8888
  application:
    name: your-service-name
  profiles:
    active: dev
```

### Running Config Server
```bash
cd flash-backend/config-server
mvn spring-boot:run
```

Access: http://localhost:8888/{application}/{profile}

---

## 2. SpringDoc OpenAPI (API Documentation)

### Location
`/flash-backend/api-docs-service/`

### Purpose
Centralized API documentation aggregation with interactive Swagger UI.

### Key Features
- Auto-generated OpenAPI 3.0 specifications
- Interactive Swagger UI
- Multi-service documentation aggregation
- Custom API documentation per service

### Dependencies Added
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webflux-ui</artifactId>
    <version>2.3.0</version>
</dependency>
```

### Per-Service Setup
Add to each microservice's `pom.xml`:
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.3.0</version>
</dependency>
```

Add to controller classes:
```java
@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User management APIs")
public class UserController {
    
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    @ApiResponse(responseCode = "200", description = "User found")
    public ResponseEntity<UserDTO> getUser(@PathVariable String id) {
        // ...
    }
}
```

### Access
- Swagger UI: http://localhost:8085/swagger-ui.html
- OpenAPI JSON: http://localhost:8085/v3/api-docs

---

## 3. Integration Tests

### Location
`/flash-backend/integration-tests/`

### Purpose
End-to-end integration testing with Testcontainers for isolated database instances.

### Key Features
- PostgreSQL Testcontainers
- REST Assured for API testing
- JUnit 5 with Spring Boot Test
- Reusable test base classes

### Structure
```
integration-tests/
├── src/test/java/ru/lomov/flash/integration/
│   ├── AbstractIntegrationTest.java
│   ├── AuthServiceIntegrationTest.java
│   └── UserServiceIntegrationTest.java (to be added)
└── src/test/resources/
    └── application-test.yml
```

### Example Test
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AuthServiceIntegrationTest extends AbstractIntegrationTest {
    
    @LocalServerPort
    private Integer port;
    
    @Test
    @DisplayName("Should register new user successfully")
    void shouldRegisterNewUser() {
        // Test implementation
    }
}
```

### Running Tests
```bash
cd flash-backend/integration-tests
mvn clean verify
```

---

## 4. CI/CD Pipeline (GitHub Actions)

### Location
`/flash-backend/.github/workflows/ci-cd.yml`

### Purpose
Automated build, test, and deployment pipeline.

### Pipeline Stages
1. **Build & Test** - Compile code and run tests
2. **Code Quality** - SonarQube analysis and Checkstyle
3. **Docker Build** - Build and push Docker images
4. **Deploy Staging** - Deploy to staging environment
5. **Deploy Production** - Deploy to production

### Required Secrets
- `SONAR_TOKEN` - SonarQube authentication
- `SONAR_ORGANIZATION` - SonarQube organization
- `KUBE_CONFIG_STAGING` - Kubernetes config for staging
- `KUBE_CONFIG_PRODUCTION` - Kubernetes config for production

### Workflow Triggers
- Push to master/main/develop branches
- Pull requests to master/main/develop

---

## 5. Distributed Tracing (Micrometer + Zipkin)

### Configuration
Located in: `configs/tracing.yml`

### Purpose
Track requests across microservices for debugging and performance analysis.

### Setup Per Service
Add dependencies:
```xml
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-brave</artifactId>
</dependency>
<dependency>
    <groupId>io.zipkin.reporter2</groupId>
    <artifactId>zipkin-reporter-brave</artifactId>
</dependency>
```

### Running Zipkin
```bash
docker run -d --name zipkin -p 9411:9411 openzipkin/zipkin
```

### Access
Zipkin UI: http://localhost:9411

### Trace Logging
Logs include trace IDs:
```
INFO [service-name,traceId,spanId] - Message
```

---

## 6. Circuit Breaker (Resilience4j)

### Configuration
Located in: `configs/resilience4j.yml`

### Purpose
Fault tolerance with circuit breaker, retry, rate limiter, and bulkhead patterns.

### Features
- **Circuit Breaker** - Prevent cascading failures
- **Retry** - Automatic retry on transient failures
- **Rate Limiter** - Limit request throughput
- **Bulkhead** - Isolate resources
- **Time Limiter** - Timeout protection

### Setup Per Service
Add dependencies:
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

### Usage Example
```java
@Service
public class UserService {
    
    @CircuitBreaker(name = "auth-service", fallbackMethod = "fallbackGetUser")
    @Retry(name = "auth-service")
    public UserDTO getUser(String userId) {
        return authClient.getUser(userId);
    }
    
    public UserDTO fallbackGetUser(String userId, Exception ex) {
        return UserDTO.defaultUser();
    }
}
```

### Monitoring Endpoints
- `/actuator/health` - Overall health
- `/actuator/circuitbreakers` - Circuit breaker states
- `/actuator/circuitevents` - Circuit breaker events

---

## 7. Message Broker (RabbitMQ)

### Configuration
Located in: `configs/rabbitmq.yml`

### Purpose
Asynchronous communication between microservices.

### Queues Defined
- `flash.notifications` - Notification events
- `flash.emails` - Email sending queue
- `flash.events` - General event bus
- `flash.analytics` - Analytics data

### Setup Per Service
Add dependencies:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

### Publisher Example
```java
@Service
public class NotificationPublisher {
    
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public void sendNotification(NotificationDTO notification) {
        rabbitTemplate.convertAndSend(
            "flash.topic",
            "notification.user." + notification.getUserId(),
            notification
        );
    }
}
```

### Consumer Example
```java
@Component
public class NotificationConsumer {
    
    @RabbitListener(queues = "flash.notifications")
    public void handleNotification(NotificationDTO notification) {
        // Process notification
    }
}
```

### Running RabbitMQ
```bash
docker run -d --name rabbitmq \
  -p 5672:5672 -p 15672:15672 \
  rabbitmq:3-management-alpine
```

Management UI: http://localhost:15672 (guest/guest)

---

## Docker Compose Setup

Create `docker-compose-enhanced.yml`:

```yaml
version: '3.8'

services:
  # Infrastructure
  postgres:
    image: postgres:15-alpine
    environment:
      POSTGRES_DB: flash_db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5432:5432"
  
  rabbitmq:
    image: rabbitmq:3-management-alpine
    ports:
      - "5672:5672"
      - "15672:15672"
  
  zipkin:
    image: openzipkin/zipkin
    ports:
      - "9411:9411"
  
  config-server:
    build: ./config-server
    ports:
      - "8888:8888"
    depends_on:
      - postgres
  
  service-discovery:
    build: ./service-discovery
    ports:
      - "8761:8761"
  
  api-gateway:
    build: ./api-gateway
    ports:
      - "8080:8080"
    depends_on:
      - config-server
      - service-discovery
```

---

## Migration Guide

### Step 1: Add Config Client Dependency
To each microservice's `pom.xml`:
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

### Step 2: Update application.yml
```yaml
spring:
  config:
    import: optional:configserver:http://localhost:8888
  application:
    name: your-service
```

### Step 3: Add Resilience4j
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
</dependency>
```

### Step 4: Add RabbitMQ
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

### Step 5: Add Tracing
```xml
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-brave</artifactId>
</dependency>
```

### Step 6: Add SpringDoc
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.3.0</version>
</dependency>
```

---

## Next Steps

1. **Update all microservices** to use Config Server
2. **Add OpenAPI annotations** to all controllers
3. **Create more integration tests** for each service
4. **Configure SonarQube** for code quality
5. **Set up Kubernetes manifests** for deployment
6. **Implement distributed transactions** with Saga pattern
7. **Add monitoring** with Prometheus and Grafana

---

## Support

For issues or questions:
- Check logs in `/flash-backend/logs/`
- Review individual service README files
- Consult Spring Cloud documentation
