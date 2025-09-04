# Admin Service

## Prohibition Notice
The use of AWS services (including S3) and Firebase services is prohibited in this project. Please refrain from implementing or integrating these services in any module.

The Admin Service is a microservice responsible for managing administrative users and their permissions within the Flash application ecosystem.

## Features

- Admin user management and authentication
- Role-based access control (SUPER_ADMIN, ADMIN, MODERATOR, SUPPORT)
- Permission management
- User activity tracking
- Admin user lifecycle management

## API Endpoints

### Create Admin User
- **POST** `/admin/users`
- Creates a new admin user
- Request Body:
```json
{
  "userId": "user123",
  "username": "admin_user",
  "email": "admin@example.com",
  "role": "ADMIN",
  "permissions": "read,write,delete"
}
```

### Get Admin User by ID
- **GET** `/admin/users/{id}`

### Get Admin User by User ID
- **GET** `/admin/users/user/{userId}`

### Get Admin User by Email
- **GET** `/admin/users/email/{email}`

### Get Admin User by Username
- **GET** `/admin/users/username/{username}`

### Get All Admin Users
- **GET** `/admin/users`

### Get Admin Users by Role
- **GET** `/admin/users/role/{role}`
- Role values: SUPER_ADMIN, ADMIN, MODERATOR, SUPPORT

### Get Active Admin Users
- **GET** `/admin/users/active`

### Update Admin User
- **PUT** `/admin/users/{id}`

### Delete Admin User
- **DELETE** `/admin/users/{id}`

### Check if User is Admin
- **GET** `/admin/users/check/{userId}`
- Returns: `true` if user is admin, `false` otherwise

### Check User Permission
- **GET** `/admin/users/permission/{userId}?permission={permission}`
- Returns: `true` if user has permission, `false` otherwise

### Update Last Login
- **POST** `/admin/users/login/{userId}`

### Deactivate Admin User
- **POST** `/admin/users/deactivate/{id}`

### Activate Admin User
- **POST** `/admin/users/activate/{id}`

## Database Schema

The service uses the following main table:
- `admin_users` - Stores admin user information and permissions

## Configuration

### Database
- PostgreSQL database named `admin_db`
- Connection configured in `application.yml`

### Service Discovery
- Registers with Eureka server at `http://localhost:8761/eureka/`

## Build and Run

### Prerequisites
- Java 17
- Maven
- PostgreSQL database
- Eureka Service Discovery

### Build
```bash
cd admin-service
mvn clean package
```

### Run
```bash
java -jar target/admin-service-1.0.0.jar
```

### Docker
```bash
docker build -t admin-service .
docker run -p 8089:8089 admin-service
```

## Port
- Default port: 8089

## Health Check
- Health endpoint: `/actuator/health`
- Available at: `http://localhost:8089/actuator/health`

## Dependencies

- Spring Boot Web
- Spring Data JPA
- Spring Cloud Eureka Client
- PostgreSQL Driver
- Lombok

## Security Considerations

- Admin users should be created with appropriate roles
- Permissions should be validated before performing sensitive operations
- Last login tracking helps monitor admin activity
- User deactivation provides a way to temporarily disable admin access
