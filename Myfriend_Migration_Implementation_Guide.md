# Myfriend Migration Implementation Guide

## 1. Authentication Migration Implementation

### 1.1 Flash-Backend Auth Service Enhancements

**Add Phone OTP Support to auth-service:**

```java
// AuthController.java - Add phone verification endpoints
@PostMapping("/phone/verify")
public ResponseEntity<?> verifyPhoneNumber(@RequestBody PhoneVerificationRequest request) {
    // Integrate with SMS service (Twilio, etc.)
    String verificationCode = smsService.sendVerificationCode(request.getPhoneNumber());
    verificationCache.put(request.getPhoneNumber(), verificationCode);
    return ResponseEntity.ok(new ApiResponse("success", "Verification code sent"));
}

@PostMapping("/phone/confirm")
public ResponseEntity<AuthResponse> confirmPhoneVerification(
        @Valid @RequestBody PhoneConfirmationRequest request) {
    String storedCode = verificationCache.get(request.getPhoneNumber());
    if (storedCode != null && storedCode.equals(request.getVerificationCode())) {
        // Create or get user
        User user = userService.findOrCreateByPhone(request.getPhoneNumber());
        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        
        return ResponseEntity.ok(new AuthResponse("success", "Phone verified", 
            accessToken, refreshToken, 86400000L, user));
    }
    return ResponseEntity.badRequest().body(new AuthResponse("error", "Invalid verification code"));
}
```

### 1.2 Android Client Auth Migration

**Create Auth Service Interface:**
```java
public interface AuthService {
    @POST("api/auth/login")
    Call<AuthResponse> login(@Body LoginRequest request);
    
    @POST("api/auth/register")
    Call<AuthResponse> register(@Body RegisterRequest request);
    
    @POST("api/auth/phone/verify")
    Call<ApiResponse> verifyPhone(@Body PhoneVerificationRequest request);
    
    @POST("api/auth/phone/confirm")
    Call<AuthResponse> confirmPhone(@Body PhoneConfirmationRequest request);
    
    @POST("api/auth/refresh")
    Call<AuthResponse> refreshToken(@Body RefreshTokenRequest request);
    
    @POST("api/auth/logout")
    Call<ApiResponse> logout();
}
```

**Auth Repository Implementation:**
```java
public class AuthRepository {
    private AuthService authService;
    private SharedPreferences prefs;
    
    public Single<AuthResponse> login(String email, String password) {
        return authService.login(new LoginRequest(email, password))
            .doOnSuccess(response -> {
                if ("success".equals(response.getStatus())) {
                    saveTokens(response.getAccessToken(), response.getRefreshToken());
                }
            });
    }
    
    private void saveTokens(String accessToken, String refreshToken) {
        prefs.edit()
            .putString("access_token", accessToken)
            .putString("refresh_token", refreshToken)
            .apply();
    }
}
```

## 2. Data Model Mapping Implementation

### 2.1 User Service Data Model

**User Entity Enhancements:**
```java
@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    private String username;
    private String email;
    private String phone;
    private String password;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    private String photo;
    private String cover;
    private String bio;
    private String website;
    
    @Column(name = "is_verified")
    private Boolean verified;
    
    @Column(name = "is_active")
    private Boolean active;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    // Firebase migration metadata
    private String firebaseUid;
    private LocalDateTime migratedAt;
}
```

### 2.2 Post Service Implementation

**Post Entity:**
```java
@Entity
@Table(name = "posts")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    private String text;
    private String type; // text, image, video
    
    @Column(name = "media_url")
    private String mediaUrl;
    
    private String privacy; // public, friends, private
    private String location;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;
    
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Like> likes;
    
    // Firebase migration fields
    private String firebasePostId;
}
```

## 3. Data Migration Scripts

### 3.1 Firebase Data Export

**Export Script (Node.js):**
```javascript
const admin = require('firebase-admin');
const fs = require('fs');

// Initialize Firebase
const serviceAccount = require('./service-account-key.json');
admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: 'https://your-project.firebaseio.com'
});

async function exportCollection(collectionName) {
  const db = admin.database();
  const ref = db.ref(collectionName);
  
  const snapshot = await ref.once('value');
  const data = snapshot.val();
  
  fs.writeFileSync(
    `./exports/${collectionName}.json`,
    JSON.stringify(data, null, 2)
  );
  
  console.log(`Exported ${collectionName}: ${Object.keys(data || {}).length} items`);
}

// Export all collections
const collections = ['Users', 'Posts', 'Groups', 'Follow', 'Story', 'Reels', 'Product'];
collections.forEach(exportCollection);
```

### 3.2 PostgreSQL Migration Script

**User Migration (Python):**
```python
import json
import psycopg2
from datetime import datetime

def migrate_users():
    with open('exports/Users.json', 'r') as f:
        users_data = json.load(f)
    
    conn = psycopg2.connect("dbname=flash_backend user=postgres")
    cur = conn.cursor()
    
    for user_id, user_data in users_data.items():
        # Transform Firebase data to PostgreSQL format
        cur.execute("""
            INSERT INTO users (
                id, username, email, phone, first_name, last_name,
                photo, cover, bio, website, verified, active,
                firebase_uid, created_at, updated_at
            ) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
        """, (
            user_id,
            user_data.get('username'),
            user_data.get('email'),
            user_data.get('phone'),
            user_data.get('firstName'),
            user_data.get('lastName'),
            user_data.get('photo'),
            user_data.get('cover'),
            user_data.get('bio'),
            user_data.get('website'),
            user_data.get('verified', False),
            user_data.get('isActive', True),
            user_id,  # Store original Firebase UID
            datetime.fromtimestamp(user_data.get('createdAt', 0) / 1000),
            datetime.now()
        ))
    
    conn.commit()
    cur.close()
    conn.close()
```

## 4. API Service Implementation

### 4.1 User Service Endpoints

**UserController.java:**
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(UserResponse.fromUser(user));
    }
    
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable String userId,
            @Valid @RequestBody UserUpdateRequest request) {
        User updatedUser = userService.updateUser(userId, request);
        return ResponseEntity.ok(UserResponse.fromUser(updatedUser));
    }
    
    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<UserResponse>> getFollowers(@PathVariable String userId) {
        List<User> followers = userService.getFollowers(userId);
        return ResponseEntity.ok(followers.stream()
            .map(UserResponse::fromUser)
            .collect(Collectors.toList()));
    }
    
    @PostMapping("/{userId}/follow/{targetUserId}")
    public ResponseEntity<ApiResponse> followUser(
            @PathVariable String userId,
            @PathVariable String targetUserId) {
        userService.followUser(userId, targetUserId);
        return ResponseEntity.ok(new ApiResponse("success", "Followed user"));
    }
}
```

### 4.2 Post Service Endpoints

**PostController.java:**
```java
@RestController
@RequestMapping("/api/posts")
public class PostController {
    
    @Autowired
    private PostService postService;
    
    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @Valid @RequestBody CreatePostRequest request,
            @RequestHeader("Authorization") String authHeader) {
        String userId = extractUserIdFromToken(authHeader);
        Post post = postService.createPost(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(PostResponse.fromPost(post));
    }
    
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable String postId) {
        Post post = postService.getPostById(postId);
        return ResponseEntity.ok(PostResponse.fromPost(post));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostResponse>> getUserPosts(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Page<Post> posts = postService.getUserPosts(userId, page, size);
        return ResponseEntity.ok(posts.getContent().stream()
            .map(PostResponse::fromPost)
            .collect(Collectors.toList()));
    }
}
```

## 5. Client App Refactoring

### 5.1 Retrofit Service Factory

**RetrofitClient.java:**
```java
public class RetrofitClient {
    private static final String BASE_URL = "https://api.flash-backend.com/";
    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;
    
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            
            // Add auth interceptor
            httpClient.addInterceptor(chain -> {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                    .header("Authorization", "Bearer " + getAccessToken())
                    .method(original.method(), original.body());
                
                return chain.proceed(requestBuilder.build());
            });
            
            // Add logging interceptor
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
            
            okHttpClient = httpClient.build();
            
            retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        }
        return retrofit;
    }
    
    public static <T> T createService(Class<T> serviceClass) {
        return getRetrofitInstance().create(serviceClass);
    }
}
```

### 5.2 Repository Pattern Implementation

**UserRepository.java:**
```java
public class UserRepository {
    private UserService userService;
    private Cache<String, User> userCache;
    
    public UserRepository() {
        userService = RetrofitClient.createService(UserService.class);
        userCache = new LruCache<>(100); // Cache 100 users
    }
    
    public Single<User> getUser(String userId) {
        // Check cache first
        User cachedUser = userCache.get(userId);
        if (cachedUser != null) {
            return Single.just(cachedUser);
        }
        
        // Fetch from API
        return userService.getUser(userId)
            .doOnSuccess(user -> userCache.put(userId, user))
            .onErrorResumeNext(error -> {
                // Handle error and potentially return cached data
                return Single.error(error);
            });
    }
    
    public Completable updateUser(User user) {
        return userService.updateUser(user.getId(), user)
            .doOnComplete(() -> userCache.put(user.getId(), user));
    }
}
```

## 6. Real-time Features with WebSocket

### 6.1 WebSocket Configuration

**WebSocketConfig.java:**
```java
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");
    }
    
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
            .setAllowedOriginPatterns("*")
            .withSockJS();
    }
    
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new AuthChannelInterceptor());
    }
}
```

### 6.2 WebSocket Message Controller

**ChatController.java:**
```java
@Controller
public class ChatController {
    
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }
    
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, 
                               SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
    
    @MessageMapping("/private.message")
    public void sendPrivateMessage(@Payload PrivateMessage message, 
                                  Principal principal) {
        messagingTemplate.convertAndSendToUser(
            message.getRecipient(), 
            "/queue/private", 
            message
        );
    }
}
```

## 7. Error Handling & Retry Logic

### 7.1 Global Error Handler

**ApiErrorHandler.java:**
```java
public class ApiErrorHandler implements ResponseErrorListener {
    @Override
    public void handleError(Retrofit retrofit, Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            int code = httpException.code();
            String message = httpException.message();
            
            switch (code) {
                case 401:
                    handleUnauthorizedError();
                    break;
                case 403:
                    handleForbiddenError();
                    break;
                case 404:
                    handleNotFoundError();
                    break;
                case 500:
                    handleServerError();
                    break;
                default:
                    handleGenericError(code, message);
            }
        } else if (throwable instanceof IOException) {
            handleNetworkError();
        } else {
            handleUnknownError(throwable);
        }
    }
    
    private void handleUnauthorizedError() {
        // Clear tokens and redirect to login
        clearAuthTokens();
        navigateToLogin();
    }
}
```

### 7.2 Retry With Backoff

**RetryWithBackoff.java:**
```java
public class RetryWithBackoff implements Function<Flowable<Throwable>, Publisher<?>> {
    private final int maxRetries;
    private final long retryDelayMillis;
    private int retryCount;
    
    public RetryWithBackoff(int maxRetries, long retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
        this.retryCount = 0;
    }
    
    @Override
    public Publisher<?> apply(Flowable<Throwable> errors) {
        return errors.flatMap(error -> {
            if (++retryCount < maxRetries) {
                long delay = retryDelayMillis * (long) Math.pow(2, retryCount - 1);
                return Flowable.timer(delay, TimeUnit.MILLISECONDS);
            }
            return Flowable.error(error);
        });
    }
}
```

## 8. Testing Implementation

### 8.1 Integration Tests

**UserServiceIntegrationTest.java:**
```java
@SpringBootTest
@AutoConfigureMockMvc
class UserServiceIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void testGetUser() throws Exception {
        mockMvc.perform(get("/api/users/{userId}", "test-user-id")
                .header("Authorization", "Bearer " + getTestToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("test-user-id"))
                .andExpect(jsonPath("$.username").value("testuser"));
    }
    
    @Test
    void testCreateUser() throws Exception {
        UserCreateRequest request = new UserCreateRequest("newuser", "new@example.com", "password");
        
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("newuser"));
    }
}
```

### 8.2 Migration Validation Tests

**DataMigrationValidationTest.java:**
```java
class DataMigrationValidationTest {
    
    @Test
    void testUserDataIntegrity() {
        // Load Firebase export
        Map<String, Object> firebaseUsers = loadFirebaseExport("Users.json");
        
        // Query PostgreSQL users
        List<User> postgresUsers = userRepository.findAll();
        
        // Validate count
        assertEquals(firebaseUsers.size(), postgresUsers.size());
        
        // Validate data integrity
        for (User user : postgresUsers) {
            Object firebaseUser = firebaseUsers.get(user.getFirebaseUid());
            assertNotNull("User not found in Firebase export: " + user.getFirebaseUid(), firebaseUser);
            
            // Validate field mapping
            Map<String, Object> fbUser = (Map<String, Object>) firebaseUser;
            assertEquals(fbUser.get("username"), user.getUsername());
            assertEquals(fbUser.get("email"), user.getEmail());
        }
    }
}
```

## 9. Deployment Configuration

### 9.1 Docker Compose Setup

**docker-compose.yml:**
```yaml
version: '3.8'
services:
  postgres:
    image: postgres:14
    environment:
      POSTGRES_DB: flash_backend
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: password
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data
  
  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"
  
  eureka-server:
    build: ./service-discovery
    ports:
      - "8761:8761"
    depends_on:
      - postgres
  
  api-gateway:
    build: ./api-gateway
    ports:
      - "8080:8080"
    depends_on:
      - eureka-server
  
  auth-service:
    build: ./auth-service
    depends_on:
      - postgres
      - eureka-server
  
  user-service:
    build: ./user-service
    depends_on:
      - postgres
      - eureka-server

volumes:
  postgres_data:
```

### 9.2 Kubernetes Deployment

**auth-service-deployment.yaml:**
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: auth-service
spec:
  replicas: 3
  selector:
    matchLabels:
      app: auth-service
  template:
    metadata:
      labels:
        app: auth-service
    spec:
      containers:
      - name: auth-service
        image: auth-service:latest
        ports:
        - containerPort: 8080
        env:
        - name: SPRING_PROFILES_ACTIVE
          value: "prod"
        - name: SPRING_DATASOURCE_URL
          value: "jdbc:postgresql://postgres:5432/flash_backend"
        resources:
          requests:
            memory: "512Mi"
            cpu: "250m"
          limits:
            memory: "1Gi"
            cpu: "500m"
```

## 10. Monitoring & Logging

### 10.1 Spring Boot Actuator Configuration

**application.yml:**
```yaml
management:
  endpoints:
    web:
      exposure:
        include: health, info, metrics, prometheus
  endpoint:
    health:
      show-details: always
  metrics:
    export:
      prometheus:
        enabled: true
```

### 10.2 Custom Metrics

**ApiMetrics.java:**
```java
@Component
public class ApiMetrics {
    private final MeterRegistry meterRegistry;
    
    private final Counter apiRequests;
    private final Timer apiResponseTime;
    
    public ApiMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        
        this.apiRequests = Counter.builder("api.requests")
            .description("Total API requests")
            .tag("application", "flash-backend")
            .register(meterRegistry);
        
        this.apiResponseTime = Timer.builder("api.response.time")
            .description("API response time")
            .register(meterRegistry);
    }
    
    public void incrementRequest(String endpoint) {
        apiRequests.increment();
    }
    
    public Timer.Sample startTimer() {
        return Timer.start(meterRegistry);
    }
    
    public void stopTimer(Timer.Sample sample, String endpoint) {
        sample.stop(apiResponseTime.tag("endpoint", endpoint));
    }
}
```

This implementation guide provides the technical foundation for migrating Myfriend from Firebase to flash-backend. Each section includes practical code examples and configuration details to help implement the migration successfully.
