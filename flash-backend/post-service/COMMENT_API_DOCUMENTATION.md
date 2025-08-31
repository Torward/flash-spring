# Comment Service API Documentation

## Overview
The Comment Service provides RESTful APIs for managing comments on posts. It supports creating, reading, updating, deleting comments, as well as like/unlike functionality.

## Base URL
```
/api/comments
```

## API Endpoints

### 1. Create Comment
**POST** `/api/comments`

Creates a new comment.

**Request Body:**
```json
{
  "postId": 1,
  "userId": 1,
  "content": "This is a comment",
  "parentCommentId": null
}
```

**Response:**
```json
{
  "id": 1,
  "postId": 1,
  "userId": 1,
  "content": "This is a comment",
  "parentCommentId": null,
  "likeCount": 0,
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T10:00:00"
}
```

### 2. Get Comment by ID
**GET** `/api/comments/{id}`

Retrieves a specific comment by its ID.

**Response:**
```json
{
  "id": 1,
  "postId": 1,
  "userId": 1,
  "content": "This is a comment",
  "parentCommentId": null,
  "likeCount": 5,
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T10:00:00"
}
```

### 3. Get Comments by Post ID
**GET** `/api/comments/post/{postId}`

Retrieves all comments for a specific post.

**Response:**
```json
[
  {
    "id": 1,
    "postId": 1,
    "userId": 1,
    "content": "First comment",
    "parentCommentId": null,
    "likeCount": 5,
    "createdAt": "2024-01-01T10:00:00",
    "updatedAt": "2024-01-01T10:00:00"
  },
  {
    "id": 2,
    "postId": 1,
    "userId": 2,
    "content": "Reply to first comment",
    "parentCommentId": 1,
    "likeCount": 2,
   极客时间
    "createdAt": "2024-01-01T10:05:00",
    "updatedAt": "2024极客时间-01-01T10:05:00"
  }
]
```

### 4. Get Comments by User ID
**GET** `/api/comments/user/{userId}`

Retrieves all comments made by a specific user.

**Response:** Same format as above.

### 5. Update Comment
**PUT** `/api/comments/{id}`

Updates the content of a comment.

**Request Body:**
```json
{
  "content": "Updated comment content"
}
```

**Response:** Updated comment object.

### 6. Delete Comment
**DELETE** `/api/comments/{id}`

Deletes a comment.

**Response:** 204 No Content

### 7. Get Comment Count by Post ID
**GET** `/api/comments/post/{postId}/count`

Returns the number of comments for a post.

**Response:** `5` (number)

### 8. Get Comment Count by User ID
**GET** `/api/comments/user/{userId}/count`

Returns the number of comments made by a user.

**Response:** `10` (number)

### 9. Like Comment
**POST** `/api/comments/{commentId}/like/{userId}`

Increments the like count for a comment.

**Response:** Updated comment object with incremented like count.

### 10. Unlike Comment
**POST** `/api/comments/{commentId}/unlike/{userId}`

Decrements the like count for a comment (if > 0).

**Response:** Updated comment object with decremented like count.

## Error Responses

### 404 Not Found
```json
{
  "timestamp": "2024-01-01T10:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Comment not found with id: 999",
  "path": "/api/comments/999"
}
```

### 400 Bad Request
Invalid request body or parameters.

## Data Types

### CommentDto
```java
public class CommentDto {
    private Long id;
    private Long postId;
    private Long userId;
    private String content;
    private Long parentCommentId;
    private Integer likeCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

## Usage Examples

### Create a comment:
```bash
curl -X POST http://localhost:8080/api/comments \
  -H "Content-Type: application/json" \
  -d '{
    "postId": 1,
    "userId": 1,
    "content": "Great post!",
    "parentCommentId": null
  }'
```

### Get comments for post:
```bash
curl http://localhost:8080/api/comments/post/1
```

### Like a comment:
```bash
curl -X POST http://localhost:8080/api/comments/1/like/1
```

## Rate Limiting
- Maximum 100 requests per minute per IP address
- Maximum 1000 comments per day per user

## Authentication
All endpoints require JWT authentication (to be implemented).

## Versioning
Current API version: v1
