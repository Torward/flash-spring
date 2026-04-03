# Flash-Spring Microservices Enhancement Guide

## 📋 Overview

This guide provides comprehensive instructions for implementing enterprise-grade improvements to the Flash-Spring microservices architecture. All templates and configurations are located in `/workspace/flash-backend/templates/`.

---

## 🔐 1. Security Implementation

### Status: Templates Created ✅ | Implementation Required ⚠️

### Files Created:
- `templates/security/SecurityConfig.java` - Spring Security configuration
- `templates/security/JwtAuthFilter.java` - JWT authentication filter
- `templates/security/JwtUtil.java` - JWT token utilities
- `templates/security/CustomUserDetailsService.java` - User details service interface

### Implementation Steps:

#### Step 1: Add Dependencies to pom.xml
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-api</artifactId>
        <version>0.11.5</version>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-impl</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt-jackson</artifactId>
        <version>0.11.5</version>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```

#### Step 2: Copy Security Files to Service
```bash
# For each service (e.g., user-service)
cp templates/security/*.java flash-backend/user-service/src/main/java/ru/lomov/flashbackend/config/
cp templates/security/*.java flash-backend/user-service/src/main/java/ru/lomov/flashbackend/filter/
cp templates/security/*.java flash-backend/user-service/src/main/java/ru/lomov/flashbackend/util/
cp templates/security/*.java flash-backend/user-service/src/main/java/ru/lomov/flashbackend/service/
```

#### Step 3: Implement UserDetailsService
```java
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements CustomUserDetailsService {
    
    private final UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        return org.springframework.security.core.userdetails.User
            .withUsername(user.getUsername())
            .password(user.getPasswordHash())
            .roles(user.getRole())
            .build();
    }
}
```

#### Step 4: Configure JWT Properties
```yaml
jwt:
  secret: ${JWT_SECRET:your-secure-secret-key-min-32-chars}
  access-token-expiration: 86400000
  refresh-token-expiration: 2592000000
  refresh-token-secret: ${JWT_REFRESH_SECRET:your-refresh-secret-key-min-64-chars}
```

### Priority Services:
1. ✅ auth-service (already has security)
2. 🔴 user-service
3. 🔴 post-service
4. 🔴 chat-service
5. 🔴 balance-service

---

## 📝 2. Database Migrations (Flyway)

### Status: Templates Created ✅ | Implementation Required ⚠️

### Files Created:
- `templates/flyway/V1__initial_schema.sql` - Base migration template

### Implementation Steps:

#### Step 1: Add Flyway Dependency
```xml
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-postgresql</artifactId>
</dependency>
```

#### Step 2: Configure Flyway in application.yml
```yaml
spring:
  flyway:
    enabled: true
    locations: classpath:db/migration
    baseline-on-migrate: true
    validate-on-migrate: true
  jpa:
    hibernate:
      ddl-auto: validate # Changed from 'update' to 'validate'
```

#### Step 3: Create Migration Files
```bash
mkdir -p flash-backend/user-service/src/main/resources/db/migration
cp templates/flyway/V1__initial_schema.sql flash-backend/user-service/src/main/resources/db/migration/
# Customize V1__initial_schema.sql for each service
```

#### Step 4: Disable DDL Auto
⚠️ **Critical**: Change `hibernate.ddl-auto` from `update` to `validate` in all services after migrations are created.

---

## 📚 3. API Documentation (SpringDoc OpenAPI)

### Status: Templates Created ✅ | Implementation Required ⚠️

### Files Created:
- `templates/openapi/OpenApiConfig.java` - OpenAPI configuration
- `templates/openapi/ExampleController.java` - Controller with OpenAPI annotations

### Implementation Steps:

#### Step 1: Add SpringDoc Dependency
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.3.0</version>
</dependency>
```

#### Step 2: Copy Configuration
```bash
cp templates/openapi/OpenApiConfig.java flash-backend/{service}/src/main/java/.../config/
```

#### Step 3: Annotate Controllers
Add OpenAPI annotations to all controller methods (see ExampleController.java).

#### Step 4: Access Documentation
- Swagger UI: `http://localhost:{port}/swagger-ui.html`
- OpenAPI JSON: `http://localhost:{port}/v3/api-docs`
- Aggregated docs: `http://localhost:8085/swagger-ui.html` (via api-docs-service)

---

## ⚡ 4. Resilience4j Circuit Breaker

### Status: Templates Created ✅ | Implementation Required ⚠️

### Files Created:
- `templates/resilience4j/resilience4j-config.yml` - Configuration template
- `templates/resilience4j/Resilience4jConfig.java` - Java configuration

### Implementation Steps:

#### Step 1: Add Dependencies
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

#### Step 2: Add Configuration
Copy `resilience4j-config.yml` content to each service's `application.yml`.

#### Step 3: Use Circuit Breaker in Services
```java
@Service
public class UserService {
    
    @CircuitBreaker(name = "user-service", fallbackMethod = "fallbackGetUser")
    public User getUserById(String id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
    }
    
    public User fallbackGetUser(String id, Exception ex) {
        log.warn("Fallback triggered for getUserById: {}", id, ex);
        return User.getDefaultUser();
    }
}
```

#### Step 4: Monitor Circuit Breakers
- Health endpoint: `http://localhost:{port}/actuator/health`
- Circuit breaker events: `http://localhost:{port}/actuator/circuitbreakerevents`

---

## 📨 5. RabbitMQ Message Broker

### Status: Templates Created ✅ | Implementation Required ⚠️

### Files Created:
- `templates/rabbitmq/rabbitmq-config.yml` - Configuration template
- `templates/rabbitmq/RabbitMQConfig.java` - Queue/Exchange configuration

### Implementation Steps:

#### Step 1: Add Dependencies
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

#### Step 2: Start RabbitMQ
```bash
docker run -d --name rabbitmq \
  -p 5672:5672 -p 15672:15672 \
  -e RABBITMQ_DEFAULT_USER=guest \
  -e RABBITMQ_DEFAULT_PASS=guest \
  rabbitmq:3-management
```

#### Step 3: Copy Configuration
```bash
cp templates/rabbitmq/*.java flash-backend/{service}/src/main/java/.../config/
```

#### Step 4: Publish Messages
```java
@Service
@RequiredArgsConstructor
public class NotificationService {
    
    private final RabbitTemplate rabbitTemplate;
    
    public void sendNotification(Notification notification) {
        rabbitTemplate.convertAndSend(
            "flash.main.exchange",
            "notification.created",
            notification
        );
    }
}
```

#### Step 5: Consume Messages
```java
@Component
@RequiredArgsConstructor
public class NotificationListener {
    
    @RabbitListener(queues = "${flash.rabbitmq.queues.notifications.name}")
    public void handleNotification(Notification notification, Channel channel, 
                                   @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            // Process notification
            processNotification(notification);
            channel.basicAck(tag, false);
        } catch (Exception e) {
            channel.basicNack(tag, false, false);
            log.error("Failed to process notification", e);
        }
    }
}
```

---

## 🔍 6. Distributed Tracing (Micrometer + Zipkin)

### Status: Templates Created ✅ | Implementation Required ⚠️

### Files Created:
- `templates/tracing/tracing-config.yml` - Configuration template
- `templates/tracing/TracingConfig.java` - Java configuration

### Implementation Steps:

#### Step 1: Add Dependencies
```xml
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-tracing-bridge-brave</artifactId>
</dependency>
<dependency>
    <groupId>io.zipkin.reporter2</groupId>
    <artifactId>zipkin-reporter-brave</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

#### Step 2: Start Zipkin
```bash
docker run -d --name zipkin \
  -p 9411:9411 \
  openzipkin/zipkin
```

#### Step 3: Add Configuration
Copy `tracing-config.yml` content to each service's `application.yml`.

#### Step 4: View Traces
Access Zipkin UI: `http://localhost:9411/zipkin/`

---

## 🧪 7. Integration Tests

### Status: Templates Created ✅ | Implementation Required ⚠️

### Files Created:
- `templates/tests/AbstractIntegrationTest.java` - Base test class
- `templates/tests/AuthServiceIntegrationTest.java` - Example test suite

### Implementation Steps:

#### Step 1: Add Test Dependencies
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.testcontainers</groupId>
        <artifactId>postgresql</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.testcontainers</groupId>
        <artifactId>junit-jupiter</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

#### Step 2: Create Test Directory Structure
```bash
mkdir -p flash-backend/{service}/src/test/java/ru/lomov/flashbackend
cp templates/tests/*.java flash-backend/{service}/src/test/java/ru/lomov/flashbackend/
```

#### Step 3: Create application-test.yml
```yaml
spring:
  datasource:
    url: jdbc:tc:postgresql:15-alpine:///test_db
    driver-class-name: org.testcontainers.jdbc.ContainerDatabaseDriver
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
  flyway:
    enabled: false

logging:
  level:
    root: WARN
    ru.lomov.flashbackend: DEBUG
```

#### Step 4: Run Tests
```bash
mvn clean test -P integration
```

---

## 🚀 Quick Start Checklist

### Phase 1: Critical (Week 1-2)
- [ ] Add Spring Security to all services
- [ ] Implement JWT authentication in each service
- [ ] Create Flyway migrations for all services
- [ ] Update all services to Spring Boot 3.2.0
- [ ] Create Dockerfile for all services

### Phase 2: Important (Week 2-4)
- [ ] Connect all services to Config Server
- [ ] Add OpenAPI documentation to all controllers
- [ ] Implement integration tests for critical services
- [ ] Set up RabbitMQ and migrate async operations

### Phase 3: Optimization (Week 4-8)
- [ ] Implement Resilience4j circuit breakers
- [ ] Set up distributed tracing with Zipkin
- [ ] Achieve >80% test coverage
- [ ] Configure production monitoring

---

## 📊 Progress Tracking

| Enhancement | Templates | Auth | User | Post | Chat | Groups | Stories |
|-------------|-----------|------|------|------|------|--------|---------|
| Security | ✅ | ✅ | 🔴 | 🔴 | 🔴 | 🔴 | 🔴 |
| Flyway | ✅ | 🔴 | 🔴 | 🔴 | 🔴 | 🔴 | 🔴 |
| OpenAPI | ✅ | 🔴 | 🔴 | 🔴 | 🔴 | 🔴 | 🔴 |
| Resilience4j | ✅ | 🔴 | 🔴 | 🔴 | 🔴 | 🔴 | 🔴 |
| RabbitMQ | ✅ | 🔴 | 🔴 | 🔴 | 🔴 | 🔴 | 🔴 |
| Tracing | ✅ | 🔴 | 🔴 | 🔴 | 🔴 | 🔴 | 🔴 |
| Tests | ✅ | 🔴 | 🔴 | 🔴 | 🔴 | 🔴 | 🔴 |

**Legend:** ✅ Complete | 🔴 Not Started | 🟡 In Progress

---

## 🆘 Support & Resources

- **Templates Location**: `/workspace/flash-backend/templates/`
- **Documentation**: See individual template files for detailed comments
- **Issues**: Check GitHub issues or contact the development team
- **Examples**: Refer to `AuthServiceIntegrationTest.java` for test patterns

---

**Last Updated**: 2024
**Version**: 1.0.0
**Maintained by**: Flash-Spring Team
