# Migration Documentation for Myfriend Application: Firebase to Flash-Backend

## 1. Introduction

### 1.1 Purpose
This document provides comprehensive guidance for migrating the Myfriend application backend from Firebase Realtime Database to the flash-backend microservices architecture. It aims to assist developers, project managers, and stakeholders in understanding the migration process, mapping, and implementation priorities.

### 1.2 Overview
The Myfriend app backend is transitioning from a Firebase-based architecture to a modular, scalable microservices system called flash-backend. This migration enhances maintainability, scalability, and performance while removing dependencies on Firebase and AWS.

### 1.3 Benefits of Migration
- Improved service separation and scalability
- Enhanced data consistency and control
- Easier maintenance and feature extension
- Reduced vendor lock-in and cost optimization

## 2. Architecture Overview

### 2.1 Firebase Realtime Database Architecture
- Centralized NoSQL database with collections representing app entities
- Real-time synchronization and client-driven data updates
- Limited service separation and scalability constraints

### 2.2 Flash-Backend Microservices Architecture
- Modular services each responsible for a specific domain (e.g., user-service, post-service)
- RESTful APIs with PostgreSQL databases per service
- Service discovery, API gateway, and WebSocket support for real-time features
- Enhanced security, scalability, and maintainability

## 3. Firebase to Flash-Backend Mapping

| Firebase Collection | Flash-Backend Service       | Status             | Notes                                  |
|---------------------|----------------------------|--------------------|----------------------------------------|
| /users              | user-service               | Fully Implemented  | Complete field and endpoint coverage   |
| /posts              | post-service               | Fully Implemented  | Missing type, vine, meme fields, replies|
| /groups             | groups-service             | Fully Implemented  |                                        |
| /follow             | follow-service             | Fully Implemented  |                                        |
| /story              | story-service              | Fully Implemented  |                                        |
| /reels              | reels-service              | Fully Implemented  | Missing like/view tracking              |
| /product            | product-service            | Fully Implemented  |                                        |
| /balance            | balance-service            | Fully Implemented  |                                        |
| /tokens             | tokens-service             | Fully Implemented  |                                        |
| /live               | live-service               | Fully Implemented  |                                        |
| /podcast            | podcast-service            | Fully Implemented  |                                        |
| /party              | party-service              | Fully Implemented  |                                        |
| /calling            | calling-service            | Fully Implemented  |                                        |
| /likes              | likes-service              | Fully Implemented  |                                        |
| /reaction           | reaction-service           | Fully Implemented  |                                        |
| /saves              | saves-service              | Fully Implemented  | Missing saves-reel functionality       |
| /reportPost         | report-service             | Partially Implemented| Specialized report services missing    |
| /chats              | chat-service               | Fully Implemented  |                                        |
| /ads                | ads-service                | Fully Implemented  |                                        |
| /location           | location-service           | Fully Implemented  |                                        |
| /verification       | verification-service       | Fully Implemented  |                                        |
| /request            | request-service            | Missing            | Critical for monetization               |
| /code               | code-service               | Missing            | Referral system                        |
| /admin              | admin-service              | Missing            | Admin rights and permissions            |
| /postExtra          | post-extra-service         | Missing            | Additional post metadata                |
| /reelsLike          | reels-like-service         | Missing            | Reel likes tracking                     |
| /reelViews          | reel-views-service         | Missing            | Reel view tracking                      |
| /savesReel          | saves-reel-service         | Missing            | Saved reels functionality               |
| /notification       | notification-service       | Fully Implemented  |                                        |

## 4. Migration Steps

### 4.1 Preparation
- Backup existing Firebase data
- Set up flash-backend environment and dependencies
- Review API gateway and service discovery configurations

### 4.2 Data Migration
- Export Firebase collections data
- Transform data to match flash-backend schemas
- Import data into respective PostgreSQL databases

### 4.3 Service Deployment
- Deploy missing services (request, code, admin, etc.)
- Apply enhancements to existing services (post-service, reels-service)
- Configure API gateway routes and security

### 4.4 Client Application Updates
- Update API endpoints to flash-backend URLs
- Adjust data models to reflect new fields and structures
- Test all user flows for functionality and performance

### 4.5 Testing and Validation
- Unit and integration testing of services
- End-to-end testing of app features
- Performance and load testing

## 5. Missing Services and Enhancements

### 5.1 Critical Missing Services

#### 5.1.1 Request Service
**Purpose**: Handle withdrawal requests for monetization  
**Firebase Collection**: `/request/{requestId}`  
**Priority**: High (affects core business functionality)

**Required Components**:
```java
// Request Entity
@Entity
@Table(name = "requests")
public class Request {
    @Id
    @UuidGenerator
    private String requestId;

    private String userId;
    private BigDecimal amount;
    private String status; // PENDING, APPROVED, REJECTED, COMPLETED
    private String paymentMethod;
    private String accountDetails;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;
    private String processedBy;
    private String rejectionReason;
}
```

**Endpoints to Implement**:
- POST `/requests` - Create withdrawal request
- GET `/requests/{requestId}` - Get request details
- GET `/requests/user/{userId}` - Get user requests
- PUT `/requests/{requestId}/approve` - Approve request (admin only)
- PUT `/requests/{requestId}/reject` - Reject request (admin only)
- GET `/requests/pending` - Get pending requests (admin only)

#### 5.1.2 Code Service
**Purpose**: Manage referral codes system  
**Firebase Collection**: `/code/{codeId}`  
**Priority**: High (essential for referral system)

**Required Components**:
```java
// ReferralCode Entity
@Entity
@Table(name = "referral_codes")
public class ReferralCode {
    @Id
    @UuidGenerator
    private String codeId;

    @Column(unique = true)
    private String codeValue;

    private String userId;
    private int maxUsage;
    private int currentUsage;
    private BigDecimal rewardAmount;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;
    private boolean isActive;
}
```

**Endpoints to Implement**:
- POST `/codes` - Create referral code
- GET `/codes/{codeId}` - Get code details
- GET `/codes/value/{codeValue}` - Get code by value
- GET `/codes/user/{userId}` - Get user codes
- POST `/codes/{codeValue}/use` - Use referral code
- PUT `/codes/{codeId}` - Update code
- DELETE `/codes/{codeId}` - Delete code
- GET `/codes/validate/{codeValue}` - Validate code

#### 5.1.3 Admin Service
**Purpose**: Manage admin users and permissions  
**Firebase Collection**: `/admin/{userId}`  
**Priority**: High (essential for user management)

**Required Components**:
```java
// AdminUser Entity
@Entity
@Table(name = "admin_users")
public class AdminUser {
    @Id
    @UuidGenerator
    private String adminId;

    private String userId;
    private String role; // SUPER_ADMIN, ADMIN, MODERATOR, SUPPORT
    private Set<String> permissions;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;
    private boolean isActive;
}
```

**Endpoints to Implement**:
- POST `/admin/users` - Create admin user
- GET `/admin/users/{adminId}` - Get admin user
- GET `/admin/users/user/{userId}` - Get admin by user ID
- PUT `/admin/users/{adminId}` - Update admin user
- DELETE `/admin/users/{adminId}` - Delete admin user
- GET `/admin/users/role/{role}` - Get admins by role
- POST `/admin/users/{adminId}/permissions` - Update permissions

### 5.2 Enhancements Needed

#### 5.2.1 Post Service Enhancements
**Missing Fields to Add**:
```java
// Add to Post entity
private String type; // "text", "image", "video", "bg", "meme"
private String vine; // Video URL for vine-type posts
private String meme; // Image URL for meme-type posts
```

**Comment Reply Functionality**:
```java
// Enhance Comment entity
private String parentCommentId; // For reply threading
private List<String> replyIds; // Child replies
private int replyCount;
```

**New Endpoints**:
- POST `/posts/{postId}/comments/{commentId}/replies` - Add reply
- GET `/posts/{postId}/comments/{commentId}/replies` - Get replies
- PUT `/posts/{postId}/type` - Update post type

#### 5.2.2 Reels Service Enhancements
**Integration Points**:
- Connect with reels-like-service
- Connect with reel-views-service

**New Endpoints**:
- GET `/reels/{reelId}/likes` - Get reel likes count
- GET `/reels/{reelId}/views` - Get reel views count
- POST `/reels/{reelId}/like` - Like reel
- DELETE `/reels/{reelId}/like` - Unlike reel
- POST `/reels/{reelId}/view` - Track view

### 5.3 Specialized Services to Implement

#### 5.3.1 Reels-Like Service
**Purpose**: Track reel likes separately  
**Firebase Collection**: `/reelsLike/{reelId}/{userId}`

**Required Components**:
```java
// ReelLike Entity
@Entity
@Table(name = "reel_likes")
public class ReelLike {
    @Id
    @UuidGenerator
    private String likeId;

    private String reelId;
    private String userId;
    private LocalDateTime likedAt;
}
```

#### 5.3.2 Reel-Views Service
**Purpose**: Track reel view analytics  
**Firebase Collection**: `/reelViews/{reelId}/{userId}`

**Required Components**:
```java
// ReelView Entity
@Entity
@Table(name = "reel_views")
public class ReelView {
    @Id
    @UuidGenerator
    private String viewId;

    private String reelId;
    private String userId;
    private LocalDateTime viewedAt;
    private Integer viewDuration; // in seconds
    private String deviceInfo;
}
```

#### 5.3.3 Saves-Reel Service
**Purpose**: Track saved reels  
**Firebase Collection**: `/savesReel/{userId}/{reelId}`

**Required Components**:
```java
// SavedReel Entity
@Entity
@Table(name = "saved_reels")
public class SavedReel {
    @Id
    @UuidGenerator
    private String saveId;

    private String userId;
    private String reelId;
    private LocalDateTime savedAt;
}
```

#### 5.3.4 Post-Extra Service
**Purpose**: Store additional post metadata  
**Firebase Collection**: `/postExtra/{postId}`

**Required Components**:
```java
// PostExtra Entity
@Entity
@Table(name = "post_extra")
public class PostExtra {
    @Id
    private String postId; // Same as Post.postId

    private String extraData; // JSON string
    private String metadata; // JSON string
    private LocalDateTime updatedAt;
}
```

#### 5.3.5 Specialized Report Services
**Services to Create**:
- `report-post-service` - Post-specific reports
- `report-reel-service` - Reel-specific reports
- `report-user-service` - User-specific reports
- `report-product-service` - Product-specific reports
- `groups-report-service` - Group-specific reports

Each specialized report service should have similar structure to the main report-service but focused on specific entity types.

## 6. Recommendations and Priorities

- Prioritize implementation of critical missing services
- Enhance existing services for feature parity
- Implement specialized services for extended functionality
- Conduct thorough testing and monitoring

## 7. Risk Assessment and Mitigation

- Data consistency and transaction management
- Service communication reliability
- Performance optimization and caching
- Security and authorization
- Real-time feature stability

---

This documentation serves as a comprehensive guide for the migration process from Firebase to flash-backend for the Myfriend application.
