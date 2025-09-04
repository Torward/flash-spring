# Report Post Service

This is a microservice for managing post reports in the Flash backend system. It provides functionality to create, read, update, and delete post reports, as well as manage their resolution status.

## Features

- Create post reports with reason and user information
- Retrieve reports by ID, post ID, or user ID
- Update report resolution status
- Get unresolved reports for moderation
- Prevent duplicate reports from the same user on the same post
- Count reports per post and total unresolved reports

## API Endpoints

### Create Report
- **POST** `/api/post-reports`
- Body: `CreatePostReportDto`

### Get Report by ID
- **GET** `/api/post-reports/{reportId}`

### Get Reports by Post ID
- **GET** `/api/post-reports/post/{postId}`

### Get Reports by User ID
- **GET** `/api/post-reports/user/{userId}`

### Get All Reports
- **GET** `/api/post-reports`

### Get Unresolved Reports
- **GET** `/api/post-reports/unresolved`

### Get Resolved Reports
- **GET** `/api/post-reports/resolved`

### Update Report
- **PUT** `/api/post-reports/{reportId}`
- Body: `UpdatePostReportDto`

### Delete Report
- **DELETE** `/api/post-reports/{reportId}`

### Get Report Count for Post
- **GET** `/api/post-reports/post/{postId}/count`

### Get Unresolved Report Count
- **GET** `/api/post-reports/unresolved/count`

### Check if User Reported Post
- **GET** `/api/post-reports/check?postId={postId}&userId={userId}`

## Data Models

### PostReport
- `reportId`: String (UUID)
- `postId`: String
- `reportedByUserId`: String
- `reason`: String
- `resolved`: boolean
- `createdAt`: LocalDateTime

### CreatePostReportDto
- `postId`: String
- `reportedByUserId`: String
- `reason`: String

### UpdatePostReportDto
- `resolved`: boolean

## Running the Service

### Prerequisites
- Java 17
- Maven
- PostgreSQL

### Configuration
Update `src/main/resources/application.yml` with your database credentials.

### Build and Run
```bash
mvn clean package
java -jar target/report-post-service-1.0.0.jar
```

### Docker
```bash
docker build -t report-post-service .
docker run -p 8087:8087 report-post-service
```

## Service Registration
This service registers with Eureka server for service discovery.

## Database
Uses PostgreSQL with JPA/Hibernate for data persistence.
