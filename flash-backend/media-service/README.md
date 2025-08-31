# Media Service

## Prohibition Notice
The use of AWS services (including S3) and Firebase services is prohibited in this project. Please refrain from implementing or integrating these services in any module.

The Media Service is a microservice responsible for handling file uploads, downloads, and management within the Flash application ecosystem.

## Features

- File upload with validation
- File download with proper content type handling
- File metadata management
- User-specific file storage
- File search functionality
- File statistics and analytics

## API Endpoints

### Upload File
- **POST** `/api/files/upload`
- Parameters: `file` (MultipartFile), `userId` (Long)
- Returns: MediaFile entity

### Get File
- **GET** `/api/files/{fileId}`
- Returns: MediaFile entity

### Get User Files
- **GET** `/api/files/user/{userId}`
- Returns: List of MediaFile entities

### Download File
- **GET** `/api/files/download/{fileId}`
- Parameters: `userId` (Long)
- Returns: File content with proper headers

### Get File URL
- **GET** `/api/files/url/{fileId}`
- Parameters: `userId` (Long)
- Returns: String URL

### Delete File
- **DELETE** `/api/files/{fileId}`
- Parameters: `userId` (Long)

### Search Files
- **GET** `/api/files/search`
- Parameters: `searchTerm` (String), `userId` (Long)
- Returns: List of MediaFile entities

## Configuration

### Database
- PostgreSQL database named `media_db`
- Connection configured in `application.yml`

### Service Discovery
- Registers with Eureka server at `http://localhost:8761/eureka/`

### File Storage
- Local file system storage (configurable path)
- Support for multiple storage types (S3, LOCAL)

## Build and Run

### Prerequisites
- Java 11
- Maven
- PostgreSQL database
- Eureka Service Discovery

### Build
```bash
cd media-service
mvn clean package
```

### Run
```bash
java -jar target/media-service-1.0.0.jar
```

### Docker
```bash
docker build -t media-service .
docker run -p 8084:8084 media-service
```

## Database Schema

The service uses the following main table:
- `media_files` - Stores file metadata and references

## Dependencies

- Spring Boot Web
- Spring Data JPA
- Spring Cloud Eureka Client
- PostgreSQL Driver
- Lombok

## Port
- Default port: 8084

## Health Check
- Health endpoint: `/actuator/health`
- Available at: `http://localhost:8084/actuator/health`
