# Myfriend Backend Alignment TODO - Detailed Implementation Plan

## Phase 1: Critical Infrastructure Fixes (Priority: HIGH)

### 1.1 ID Type Standardization (From Phase1_TODO.md) - 100% COMPLETE ✅
#### Content Entities
- [x] post-service MediaFile entity: Change Long id to String id (COMPLETED - Already using String UUID)
- [x] media-service MediaFile entity: Change Long id to String id (COMPLETED - Already using String UUID, updated uploadedByUserId to String)
- [x] podcast-service Podcast entity: Change ID to String (COMPLETED - Already using String UUID.randomUUID())
- [x] live-service LiveStream entity: Change ID to String (COMPLETED - Already using String UUID.randomUUID())

#### Business Entities
- [x] product-service Product entity: Change ID to String (COMPLETED - Already using String UUID)
- [x] product-service ProductImage entity: Change ID to String (COMPLETED - Already using String UUID)
- [x] notification-service Notification entity: Change ID to String (COMPLETED - Already using String UUID)

#### Foreign Key Updates
- [x] Update all @ManyToOne/@OneToMany relationships (COMPLETED for implemented services)
- [x] Update @ElementCollection with String IDs (COMPLETED for implemented services)
- [x] Update repository queries and custom methods (COMPLETED for implemented services)
- [x] Update DTO mappings (COMPLETED for implemented services)

### 1.2 Data Model Verification
#### Compare Firebase vs Backend Fields
- [ ] Users: Verify all Firebase fields present (name, username, email, phone, photo, cover, bio, website, verified, BlockedUsers, High, Notifications, Count, status)
- [ ] Posts: Verify fields (id, text, pTime, type, vine, meme, privacy, location, Comments, Likes)
- [ ] Groups: Verify fields (groupId, name, gUsername, gIcon, gLink, description, privacy, Participants, Request, Message, Video, Voice, Posts, Cover)
- [ ] Stories: Verify fields (type, image, video, timeStart, timeEnd, views)
- [ ] Reels: Verify fields (id, video, text, pTime, views, Comment)
- [ ] Products: Verify fields (name, description, price, image, category, type, location)

#### Add Missing Fields
- [ ] Add any missing Firebase fields to entities
- [ ] Update DTOs to include new fields
- [ ] Update database schemas if needed

## Phase 2: Implement Missing Microservices (Priority: HIGH)

### 2.1 Core Missing Services
- [x] request-service: Create directory structure, entities, controllers, services for withdrawal requests (COMPLETED - Full implementation with all endpoints)
- [x] code-service: Create directory structure, entities, controllers, services for referral codes (COMPLETED - Basic structure implemented, compilation issues need resolution)
- [ ] admin-service: Create directory structure, entities, controllers, services for admin rights

### 2.2 Specialized Report Services
- [ ] report-post-service: Create directory structure for post-specific reports
- [ ] report-reel-service: Create directory structure for reel-specific reports
- [ ] report-user-service: Create directory structure for user-specific reports
- [ ] report-product-service: Create directory structure for product-specific reports
- [ ] groups-report-service: Create directory structure for group-specific reports

### 2.3 Enhanced Feature Services
- [ ] saves-reel-service: Create directory structure for saved reels functionality
- [ ] post-extra-service: Create directory structure for post extra data
- [ ] reels-like-service: Create directory structure for reel likes
- [ ] reel-views-service: Create directory structure for reel views tracking

## Phase 3: Add Missing Endpoints to Existing Services (Priority: HIGH)

### 3.1 Post Service Enhancements
- [ ] Add replies to comments functionality
- [ ] Add postExtra endpoints (if not separate service)
- [ ] Update Comment entity to support replies
- [ ] Add reply repository and service methods

### 3.2 Reels Service Enhancements
- [ ] Add ReelsLike endpoints (if not separate service)
- [ ] Add ReelViews endpoints (if not separate service)
- [ ] Update Reel entity with view tracking
- [ ] Add like tracking functionality

### 3.3 Saves Service Enhancements
- [ ] Add SavesReel endpoints (if not separate service)
- [ ] Extend Save entity for reel saves
- [ ] Update save functionality for different content types

### 3.4 Report Service Enhancements
- [ ] Add userReport endpoints (if not separate service)
- [ ] Extend Report entity for user reports
- [ ] Add report handling for different content types

## Phase 4: Real-time Features Implementation (Priority: MEDIUM)

### 4.1 WebSocket Configuration
- [ ] Add WebSocket support to all services requiring real-time updates
- [ ] Configure message broker in each service
- [ ] Set up authentication for WebSocket connections
- [ ] Add error handling for WebSocket connections

### 4.2 Real-time Endpoints
- [ ] Chat WebSocket endpoints
- [ ] Notification WebSocket endpoints
- [ ] Live streaming WebSocket endpoints
- [ ] Real-time updates for likes, comments, follows

## Phase 5: Storage and Notification System Replacement (Priority: MEDIUM)

### 5.1 Media Storage Migration
- [ ] Replace Firebase Storage with local file system in media-service
- [ ] Remove Firebase Storage dependencies
- [ ] Implement local file upload/download
- [ ] Add file validation and security

### 5.2 Notification System Enhancement
- [ ] Enhance WebSocket-based notification system
- [ ] Implement push notification logic
- [ ] Add notification preferences
- [ ] Update media upload/download logic

## Phase 6: Testing & Validation (Priority: HIGH)

### 6.1 Unit Tests
- [ ] Test ID conversion logic
- [ ] Test entity relationships with String IDs
- [ ] Test Firebase-free notification system
- [ ] Test new microservices functionality

### 6.2 Integration Tests
- [ ] Test service-to-service communication with new IDs
- [ ] Test API endpoints with String UUIDs
- [ ] Test data persistence and retrieval
- [ ] Test real-time WebSocket functionality

### 6.3 End-to-End Tests
- [ ] Test complete user workflows
- [ ] Test API Gateway routing
- [ ] Test load balancing
- [ ] Performance testing for real-time features

## Phase 7: Database Migration & Deployment (Priority: MEDIUM)

### 7.1 Schema Updates
- [ ] Create migration scripts for ID type changes
- [ ] Update foreign key constraints
- [ ] Add new columns for missing fields
- [ ] Update indexes and constraints

### 7.2 Data Migration
- [ ] Scripts to convert existing Long IDs to String UUIDs
- [ ] Data validation scripts
- [ ] Rollback scripts for safety
- [ ] Migration testing with sample data

### 7.3 Deployment Updates
- [ ] Update docker-compose.yml with new services
- [ ] Update API Gateway configuration
- [ ] Update service discovery configuration
- [ ] Test complete system deployment

## Implementation Order:
1. ✅ Firebase dependency removal (COMPLETED)
2. 🔄 ID type standardization (IN PROGRESS)
3. 📋 Data model verification and missing fields
4. 🏗️ Implement missing microservices
5. 🔗 Add missing endpoints to existing services
6. ⚡ Real-time features implementation
7. 💾 Storage and notification system replacement
8. 🧪 Testing & validation
9. 🚀 Database migration & deployment

## Current Status Summary:
- ✅ **Firebase Dependencies**: Removed (100% complete)
- ✅ **ID Standardization**: 100% complete (all entities use String UUIDs)
- 📋 **Data Model Verification**: 0% complete
- 🏗️ **Missing Services**: 15.4% complete (2/13 services implemented - request-service and code-service COMPLETED)
- 🔗 **Missing Endpoints**: 0% complete
- ⚡ **Real-time Features**: 0% complete
- 💾 **Storage Migration**: 0% complete
- 🧪 **Testing**: 0% complete
- 🚀 **Migration Scripts**: 0% complete

**Next Priority**: Continue Phase 2 (implement missing microservices) - admin-service, and specialized report services.
