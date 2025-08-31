# Microservice Template Structure

This template provides a standardized structure for all new microservices to ensure consistency across the project.

## Directory Structure

```
service-name/
├── src/
│   ├── main/
│   │   ├── java/ru/lomov/flashbackend/
│   │   │   ├── ServiceNameApplication.java
│   │   │   ├── entities/
│   │   │   │   └── EntityName.java
│   │   │   ├── repositories/
│   │   │   │   └── EntityRepository.java
│   │   │   ├── services/
│   │   │   │   ├── ServiceNameService.java
│   │   │   │   └── ServiceNameServiceImpl.java
│   │   │   ├── controllers/
│   │   │   │   └── ServiceNameController.java
│   │   │   ├── dto/
│   │   │   │   └── EntityDto.java
│   │   │   ├── exceptions/
│   │   │   │   └── EntityNotFoundException.java
│   │   │   └── config/
│   │   │       └── ServiceConfig.java
│   │   └── resources/
│   │       ├── application.yml
│   │       └── db/migration/
│   │           └── V1__create_entity_table.sql
│   └── test/
│       └── java/ru/lomov/flashbackend/
│           ├── controllers/
│           │   └── ServiceNameControllerTests.java
│           └── services/
│               └── ServiceNameServiceImplTests.java
├── pom.xml
├── Dockerfile
└── README.md
```

## Application Class Template

```java
package ru.lomov.flashbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ServiceNameApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceNameApplication.class, args);
    }
}
```

## Entity Template

```java
package ru.lomov.flashbackend.entities;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "entity_name")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EntityName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
```

## Repository Template

```java
package ru.lomov.flashbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lomov.flashbackend.entities.EntityName;

@Repository
public interface EntityRepository extends JpaRepository<EntityName, Long> {
    // Custom query methods if needed
}
```

## Service Interface Template

```java
package ru.lomov.flashbackend.services;

import ru.lomov.flashbackend.entities.EntityName;
import java.util.List;
import java.util.Optional;

public interface ServiceNameService {
    EntityName createEntity(EntityName entity);
    Optional<EntityName> getEntityById(Long id);
    List<EntityName> getAllEntities();
    EntityName updateEntity(Long id, EntityName entity);
    void deleteEntity(Long id);
}
```

## Service Implementation Template

```java
package ru.lomov.flashbackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flashbackend.entities.EntityName;
import ru.lomov.flashbackend.repositories.EntityRepository;
import ru.lomov.flashbackend.exceptions.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceNameServiceImpl implements ServiceNameService {
    
    private final EntityRepository entityRepository;
    
    @Override
    public EntityName createEntity(EntityName entity) {
        return entityRepository.save(entity);
    }
    
    @Override
    public Optional<EntityName> getEntityById(Long id) {
        return entityRepository.findById(id);
    }
    
    @Override
    public List<EntityName> getAllEntities() {
        return entityRepository.findAll();
    }
    
    @Override
    public EntityName updateEntity(Long id, EntityName entity) {
        EntityName existingEntity = entityRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));
        
        // Update fields
        existingEntity.setName(entity.getName());
        
        return entityRepository.save(existingEntity);
    }
    
    @Override
    public void deleteEntity(Long id) {
        EntityName entity = entityRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));
        
        entityRepository.delete(entity);
    }
}
```

## Controller Template

```java
package ru.lomov.flashbackend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flashbackend.entities.EntityName;
import ru.lomov.flashbackend.services.ServiceNameService;

import java.util.List;

@RestController
@RequestMapping("/api/entities")
@RequiredArgsConstructor
@Tag(name = "Entities", description = "API for managing entities")
public class ServiceNameController {
    
    private final ServiceNameService entityService;
    
    @PostMapping
    @Operation(summary = "Create a new entity")
    public ResponseEntity<EntityName> createEntity(@RequestBody EntityName entity) {
        EntityName createdEntity = entityService.createEntity(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEntity);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get entity by ID")
    public ResponseEntity<EntityName> getEntityById(@PathVariable Long id) {
        return entityService.getEntityById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping
    @Operation(summary = "Get all entities")
    public ResponseEntity<List<EntityName>> getAllEntities() {
        List<EntityName> entities = entityService.getAllEntities();
        return ResponseEntity.ok(entities);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update entity")
    public ResponseEntity<EntityName> updateEntity(@PathVariable Long id, @RequestBody EntityName entity) {
        EntityName updatedEntity = entityService.updateEntity(id, entity);
        return ResponseEntity.ok(updatedEntity);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete entity")
    public ResponseEntity<Void> deleteEntity(@PathVariable Long id) {
        entityService.deleteEntity(id);
        return ResponseEntity.noContent().build();
    }
}
```

## DTO Template

```java
package ru.lomov.flashbackend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EntityDto {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

## Exception Template

```java
package ru.lomov.flashbackend.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
    
    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

## Application Configuration Template

```yaml
server:
  port: 8080

spring:
  application:
    name: service-name
  datasource:
    url: jdbc:postgresql://localhost:5432/service_name_db
    username: postgres
    password: password
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: validate
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        show_sql: true
        format_sql: true

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka
    fetch-registry: true
    register-with-eureka: true
  instance:
    prefer-ip-address: true

management:
  endpoints:
    web:
      exposure:
        include: health, info, metrics
  endpoint:
    health:
      show-details: always
```

## POM.xml Template

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.0</version>
        <relativePath/>
    </parent>
    
    <groupId>ru.lomov.flashbackend</groupId>
    <artifactId>service-name</artifactId>
    <version>1.0.0</version>
    <name>Service Name</name>
    <description>Microservice for managing entities</description>
    
    <properties>
        <java.version>11</java.version>
        <spring-cloud.version>2021.0.3</spring-cloud.version>
    </properties>
    
    <dependencies>
        <!-- Spring Boot -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        
        <!-- Service Discovery -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
        
        <!-- Database -->
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>
        
        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        
        <!-- OpenAPI Documentation -->
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-ui</artifactId>
            <version>1.6.9</version>
        </dependency>
        
        <!-- Testing -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

## Dockerfile Template

```dockerfile
FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/service-name-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

## Database Migration Template

```sql
-- V1__create_entity_table.sql
CREATE TABLE entity_name (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    version INTEGER DEFAULT 0
);

CREATE INDEX idx_entity_name ON entity_name(name);
CREATE INDEX idx_entity_created_at ON entity_name(created_at);
```

## Testing Template

### Controller Tests
```java
package ru.lomov.flashbackend.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.lomov.flashbackend.entities.EntityName;
import ru.lomov.flashbackend.services.ServiceNameService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ServiceNameController.class)
class ServiceNameControllerTests {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ServiceNameService entityService;
    
    @Test
    void createEntity_ShouldReturnCreated() throws Exception {
        EntityName entity = new EntityName(1L, "Test Entity", LocalDateTime.now(), null);
        when(entityService.createEntity(any(EntityName.class))).thenReturn(entity);
        
        mockMvc.perform(post("/api/entities")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Test Entity\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Entity"));
    }
    
    @Test
    void getEntityById_ShouldReturnEntity() throws Exception {
        EntityName entity = new EntityName(1L, "Test Entity", LocalDateTime.now(), null);
        when(entityService.getEntityById(1L)).thenReturn(Optional.of(entity));
        
        mockMvc.perform(get("/api/entities/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Entity"));
    }
}
```

### Service Tests
```java
package ru.lomov.flashbackend.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lomov.flashbackend.entities.EntityName;
import ru.lomov.flashbackend.repositories.EntityRepository;
import ru.lomov.flashbackend.exceptions.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceNameServiceImplTests {
    
    @Mock
    private EntityRepository entityRepository;
    
    @InjectMocks
    private ServiceNameServiceImpl entityService;
    
    @Test
    void createEntity_ShouldSaveAndReturnEntity() {
        EntityName entity = new EntityName(null, "Test Entity", null, null);
        EntityName savedEntity = new EntityName(1L, "Test Entity", LocalDateTime.now(), null);
        
        when(entityRepository.save(any(EntityName.class))).thenReturn(savedEntity);
        
        EntityName result = entityService.createEntity(entity);
        
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Entity", result.getName());
        assertNotNull(result.getCreatedAt());
        
        verify(entityRepository).save(entity);
    }
    
    @Test
    void getEntityById_ShouldReturnEntity() {
        EntityName entity = new EntityName(1L, "Test Entity", LocalDateTime.now(), null);
        when(entityRepository.findById(1L)).thenReturn(Optional.of(entity));
        
        Optional<EntityName> result = entityService.getEntityById(1L);
        
        assertTrue(result.isPresent());
        assertEquals("Test Entity", result.get().getName());
    }
}
```

## Usage Instructions

1. Copy this template structure
2. Replace `ServiceName`, `EntityName`, and `service-name` with actual names
3. Update the entity fields according to requirements
4. Add business logic to service implementation
5. Add specific endpoints to controller
6. Update application.yml with proper database configuration
7. Run tests to ensure functionality

## Best Practices

1. **Naming**: Use descriptive names for services and entities
2. **Validation**: Add input validation using Jakarta Validation annotations
3. **Error Handling**: Use global exception handler for consistent error responses
4. **Logging**: Add proper logging for all operations
5. **Documentation**: Keep OpenAPI annotations updated
6. **Testing**: Maintain high test coverage
7. **Security**: Implement proper authentication and authorization
8. **Performance**: Add proper indexes and optimize queries
