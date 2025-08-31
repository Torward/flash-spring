# Groups Service

A microservice for managing user groups in the Flash application. This service provides CRUD operations for groups, including creating, updating, deleting, and searching groups.

## Features

- **Group Management**: Create, read, update, and delete groups
- **Group Search**: Search groups by name
- **Participant Management**: Add and remove participants from groups
- **Privacy Settings**: Support for public and private groups
- **RESTful API**: Comprehensive API endpoints for group operations
- **Service Discovery**: Integration with Eureka service discovery
- **OpenAPI Documentation**: Auto-generated API documentation

## API Endpoints

### Group Operations

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/groups` | Create a new group |
| GET | `/api/groups/{id}` | Get group by ID |
| GET | `/api/groups` | Get all groups |
| PUT | `/api/groups/{id}` | Update group |
| DELETE | `/api/groups/{id}` | Delete group |
| GET | `/api/groups/creator/{creatorId}` | Get groups by creator ID |
| GET | `/api/groups/participant/{userId}` | Get groups by participant ID |
| GET | `/api/groups/search?name={name}` | Search groups by name |
| POST | `/api/groups/{groupId}/participants/{userId}` | Add participant to group |
| DELETE | `/api/groups/{groupId}/participants/{userId}` | Remove participant from group |

### Group Entity Structure

```json
{
  "id": 1,
  "name": "Test Group",
  "description": "Group description",
  "creatorId": 123,
  "participants": [123, 456, 789],
  "privacy": "public",
  "createdAt": "2023-01-01T10:00:00Z"
}
```

## Configuration

### Application Properties

The service is configured via `application.yml`:

```yaml
server:
  port: 8083

spring:
  application:
    name: groups-service
  datasource:
    url: jdbc:postgresql://localhost:5432/groups_db
    username: postgres
    password: password
  jpa:
    hibernate:
      ddl-auto: validate

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka
```

### Database Setup

Create a PostgreSQL database named `groups_db`:

```sql
CREATE DATABASE groups_db;
```

## Running the Service

### Local Development

1. Build the project:
```bash
mvn clean package
```

2. Run the application:
```bash
java -jar target/groups-service-1.0.0.jar
```

### Docker

1. Build the Docker image:
```bash
docker build -t groups-service .
```

2. Run the container:
```bash
docker run -p 8083:8083 groups-service
```

## Dependencies

- Spring Boot Web
- Spring Data JPA
- PostgreSQL Driver
- Spring Cloud Eureka Client
- Lombok
- Spring Boot Actuator
- SpringDoc OpenAPI

## Testing

Run tests with:
```bash
mvn test
```

## Health Check

The service provides health endpoints via Spring Boot Actuator:

- Health: `http://localhost:8083/actuator/health`
- Info: `http://localhost:8083/actuator/info`
- Metrics: `http://localhost:8083/actuator/metrics`

## API Documentation

Swagger UI is available at:
```
http://localhost:8083/swagger-ui.html
```

OpenAPI documentation is available at:
```
http://localhost:8083/v3/api-docs
```

## Integration

The Groups Service integrates with:
- Service Discovery (Eureka)
- API Gateway
- Other microservices in the Flash ecosystem

## Error Handling

The service provides proper error handling with appropriate HTTP status codes:
- 404: Group not found
- 400: Bad request
- 500: Internal server error
