# Myfriend Backend Alignment - Analysis and Implementation Plan

## Analysis Summary

### Existing Microservices (20 services)
- [x] api-gateway: API routing and gateway
- [x] auth-service: Authentication and authorization
- [x] balance-service: User balance management
- [x] follow-service: User following/followers
- [x] groups-service: Group management
- [x] likes-service: Post likes
- [x] live-service: Live streaming
- [x] media-service: File/media management
- [x] notification-service: Notifications
- [x] party-service: Watch party functionality (implemented)
- [x] podcast-service: Podcast management
- [x] post-service: Post management
- [x] product-service: Product/marketplace
- [x] reaction-service: Post reactions
- [x] reels-service: Video reels
- [x] saves-service: Saved posts/reels
- [x] service-discovery: Eureka service discovery
- [x] story-service: User stories
- [x] tokens-service: FCM tokens
- [x] user-service: User profiles

### Missing Services (7 services)
- [ ] calling-service: Voice/video call functionality
- [ ] ads-service: Advertisement management
- [ ] location-service: Location-based features
- [ ] verification-service: User verification process
- [ ] report-service: Content moderation system
- [ ] code-service: Referral code system
- [ ] request-service: Withdrawal request handling

### Firebase Endpoint Alignment
- [x] Users: /users (matches, but needs string IDs)
- [x] Posts: /posts (matches, but needs string IDs)
- [x] Groups: /groups (matches)
- [x] Follow: /follow (matches)
- [x] Story: /story (matches)
- [x] Reels: /reels (matches)
- [x] Product: /product (matches)
- [x] Balance: /balance (matches)
- [x] Tokens: /tokens (matches)
- [x] Live: /live (matches)
- [x] Podcast: /podcast (matches)
- [x] Party: /party (matches)
- [ ] Calling: /calling (missing service)
- [ ] Likes: /likes (exists, but path is /likes/{postId}/{userId})
- [ ] Reaction: /reaction (exists)
- [ ] Saves: /saves (exists)
- [ ] Report: /report* (missing service)
- [ ] Ads: /ads (missing service)
- [ ] Location: /location (missing service)
- [ ] Verification: /verification (missing service)
- [ ] Code: /code (missing service)
- [ ] Request: /request (missing service)

### Key Issues Identified
1. **ID Types**: Backend uses Long IDs, Firebase uses String IDs
2. **Firebase Dependencies**: Some services still have Firebase Admin SDK
3. **Real-time Features**: No WebSocket/SSE implementation
4. **Storage**: No alternative to Firebase Storage
5. **Notifications**: Firebase FCM dependency
6. **Data Structure**: Relational vs NoSQL differences
7. **Chat**: Embedded in services, not separate

## Updated Implementation Plan

## Phase 1: Critical Fixes and Firebase Removal
### 1. Fix ID Types
- [ ] Analyze all entities and their dependencies
- [ ] Change core entities (User, AppUser) to String UUIDs
- [ ] Update dependent entities (Post, MediaFile, Group, etc.)
- [ ] Update all foreign key references
- [ ] Update repositories, services, controllers, and DTOs
- [ ] Test ID changes in each service

#### Detailed ID Change Plan:
**Step 1: Core User Entities**
- [x] auth-service User entity: Change Long id to String id (UUID)
- [x] Add JPA dependency to auth-service pom.xml
- [x] user-service AppUser entity: Already String, verify consistency
- [x] Update auth-service repositories, services, controllers
- [ ] Update user-service references if needed

**Step 1.1: Party Entity ID Conversion**
- [x] party-service Party entity: Change Long id to String id with @UuidGenerator
- [x] party-service PartyDto: Change Long id to String id
- [x] party-service PartyController: Update path variables to String
- [x] party-service PartyService interface: Update method signatures to String
- [x] party-service PartyServiceImpl: Update method implementations to String
- [x] Test Party service with String UUIDs (compilation issues resolved)

**Step 2: Content Entities**
- [x] post-service Post entity: Change Long postId to String postId
- [ ] post-service MediaFile entity: Change Long id to String id
- [ ] media-service MediaFile entity: Change Long id to String id
- [ ] reels-service Reel entity: Change ID to String
- [ ] story-service Story entity: Change ID to String
- [ ] podcast-service Podcast entity: Change ID to String
- [ ] live-service LiveStream entity: Change ID to String

**Step 3: Social Entities**
- [ ] groups-service Group entity: Change Long id to String id
- [ ] party-service Party entity: Change Long id to String id
- [ ] follow-service Follow entity: Change IDs to String
- [ ] likes-service Like entity: Change IDs to String
- [ ] reaction-service Reaction entity: Change IDs to String
- [ ] saves-service Save entity: Change IDs to String

**Step 4: Business Entities**
- [ ] product-service Product entity: Change ID to String
- [ ] product-service ProductImage entity: Change ID to String
- [ ] balance-service Balance entity: Change ID to String
- [ ] tokens-service Token entity: Change ID to String
- [ ] notification-service Notification entity: Change ID to String

**Step 5: Foreign Key Updates**
- [ ] Update all @ManyToOne/@OneToMany relationships
- [ ] Update @ElementCollection with String IDs
- [ ] Update repository queries and custom methods
- [ ] Update DTO mappings

### 2. Remove Firebase Dependencies
- [x] Remove Firebase Admin SDK from notification-service
- [x] Remove Firebase dependencies from reels-service
- [x] Remove S3 references from post-service
- [ ] Verify no Firebase/AWS in any service

### 3. Update Data Models
- [ ] Add missing Firebase fields to entities
- [ ] Align field names with Firebase structure
- [ ] Add migration metadata

## Phase 2: Missing Services Implementation
### 4. Implement Missing Services
- [ ] calling-service: Voice/video calls
- [ ] ads-service: Ads management
- [ ] location-service: Location features
- [ ] verification-service: User verification
- [ ] report-service: Content reports
- [ ] code-service: Referral codes
- [ ] request-service: Withdrawal requests

### 5. Chat Service
- [ ] Extract chat functionality from groups/live/party/podcast
- [ ] Create unified chat-service
- [ ] Implement real-time messaging

## Phase 3: Real-time and Storage
### 6. Real-time Features
- [ ] Implement WebSocket support
- [ ] Add Server-Sent Events (SSE)
- [ ] Presence system with heartbeat

### 7. Storage Solution
- [ ] Implement MinIO for file storage
- [ ] Update media-service for MinIO
- [ ] File upload/download endpoints

### 8. Notification Solution
- [ ] Implement alternative push service
- [ ] Notification delivery system

## Phase 4: API Alignment and Testing
### 9. API Gateway Updates
- [ ] Update routes for all services
- [ ] Ensure path consistency
- [ ] Add missing routes

### 10. Testing
- [ ] Unit tests for all services
- [ ] Integration tests
- [ ] End-to-end testing

## Phase 5: Deployment
### 11. Production Setup
- [ ] Update docker-compose.yml
- [ ] Environment configuration
- [ ] Monitoring and logging

## Current Progress:
- [x] Analysis: 100% complete
- [x] Party service: 100% complete
- [x] ID Types Verification: Core entities (User, Post) already use String UUIDs
- [x] Firebase Dependencies Check: Removed from reels-service, still present in notification-service
- [x] Extra TODOs Check: No extra TODO comments found in codebase
- [ ] Phase 1: 10% complete (ID types verified, Firebase removal pending)
- [ ] Phase 2: 0% complete
- [ ] Phase 3: 0% complete
- [ ] Phase 4: 0% complete
- [ ] Phase 5: 0% complete

## Next Steps:
1. Remove Firebase dependencies from notification-service
2. Verify and update data models to match Firebase fields
3. Implement missing services (calling, ads, location, verification, report, code, request)
4. Add real-time features (WebSocket/SSE)
5. Implement alternative storage solution (MinIO)
6. Complete API alignment and testing
7. Update docker-compose.yml and deployment configuration
