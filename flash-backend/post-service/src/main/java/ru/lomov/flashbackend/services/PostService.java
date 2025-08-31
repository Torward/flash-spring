package ru.lomov.flashbackend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.lomov.flashbackend.entities.Post;
import ru.lomov.flashbackend.exceptions.PostNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PostService {
    
    // Basic CRUD operations
    Post createPost(Post post);
    Post getPostById(Long postId) throws PostNotFoundException;
    Post updatePost(Long postId, Post post) throws PostNotFoundException;
    void deletePost(Long postId, Long userId) throws PostNotFoundException;
    void archivePost(Long postId, Long userId) throws PostNotFoundException;
    void restorePost(Long postId, Long userId) throws PostNotFoundException;
    
    // User posts
    Page<Post> getUserPosts(Long userId, Pageable pageable);
    Page<Post> getUserPublicPosts(Long userId, Pageable pageable);
    Page<Post> getUserDraftPosts(Long userId, Pageable pageable);
    Page<Post> getUserArchivedPosts(Long userId, Pageable pageable);
    
    // Feed and discovery
    Page<Post> getFeedPosts(Long userId, Pageable pageable);
    Page<Post> getDiscoverPosts(Pageable pageable);
    Page<Post> getTrendingPosts(Pageable pageable);
    Page<Post> getPopularPosts(Pageable pageable);
    
    // Search and filtering
    Page<Post> searchPosts(String query, Pageable pageable);
    Page<Post> searchPostsByHashtag(String hashtag, Pageable pageable);
    Page<Post> searchPostsByLocation(Double latitude, Double longitude, Double radius, Pageable pageable);
    Page<Post> searchPostsByMention(Long mentionedUserId, Pageable pageable);
    
    // Content moderation
    Page<Post> getPostsForModeration(Pageable pageable);
    Post moderatePost(Long postId, String status, String reason, Double score) throws PostNotFoundException;
    Page<Post> getModeratedPosts(String status, Pageable pageable);
    
    // Analytics and insights
    Map<String, Object> getUserPostStats(Long userId);
    Map<String, Object> getPostAnalytics(Long postId) throws PostNotFoundException;
    Map<String, Object> getTrendingHashtags(int limit);
    Map<String, Object> getLocationInsights(Double latitude, Double longitude, Double radius);
    
    // Engagement operations
    Post likePost(Long postId, Long userId) throws PostNotFoundException;
    Post unlikePost(Long postId, Long userId) throws PostNotFoundException;
    Post viewPost(Long postId, Long userId) throws PostNotFoundException;
    Post savePost(Long postId, Long userId) throws PostNotFoundException;
    Post unsavePost(Long postId, Long userId) throws PostNotFoundException;
    Post sharePost(Long postId, Long userId) throws PostNotFoundException;
    
    // Comments management (delegated to comment service)
    Post addComment(Long postId, Long userId, String comment) throws PostNotFoundException;
    Post removeComment(Long postId, Long commentId, Long userId) throws PostNotFoundException;
    
    // Poll operations
    Post voteInPoll(Long postId, Long userId, int optionIndex) throws PostNotFoundException;
    Post createPoll(Long userId, String question, List<String> options, LocalDateTime endsAt);
    Post closePoll(Long postId, Long userId) throws PostNotFoundException;
    
    // Live stream operations
    Post startLiveStream(Long userId, String title, String description);
    Post endLiveStream(Long postId, Long userId) throws PostNotFoundException;
    Post updateLiveViewers(Long postId, int viewerCount) throws PostNotFoundException;
    
    // Scheduled posts
    Page<Post> getScheduledPosts(Long userId, Pageable pageable);
    Post schedulePost(Post post, LocalDateTime scheduleTime);
    Post publishScheduledPost(Long postId) throws PostNotFoundException;
    void cancelScheduledPost(Long postId, Long userId) throws PostNotFoundException;
    
    // Sponsored content
    Page<Post> getSponsoredPosts(Pageable pageable);
    Post createSponsoredPost(Post post, String sponsorId, String campaignId);
    Post updateSponsorship(Long postId, String sponsorId, String campaignId, Double cpmRate) throws PostNotFoundException;
    
    // Bulk operations
    List<Post> bulkCreatePosts(List<Post> posts);
    void bulkDeletePosts(List<Long> postIds, Long userId);
    void bulkArchivePosts(List<Long> postIds, Long userId);
    void bulkPublishPosts(List<Long> postIds);
    
    // Export and backup
    String exportUserPosts(Long userId, String format);
    void backupPosts(LocalDateTime beforeDate);
    void restorePostsFromBackup(String backupId);
    
    // Content recommendations
    Page<Post> getRecommendedPosts(Long userId, Pageable pageable);
    Page<Post> getSimilarPosts(Long postId, Pageable pageable);
    Page<Post> getPostsYouMayLike(Long userId, Pageable pageable);
    
    // Social features
    Page<Post> getPostsFromFollowing(Long userId, Pageable pageable);
    Page<Post> getPostsLikedByUser(Long userId, Pageable pageable);
    Page<Post> getPostsSavedByUser(Long userId, Pageable pageable);
    Page<Post> getPostsSharedByUser(Long userId, Pageable pageable);
    
    // Administrative operations
    Page<Post> getAllPosts(Pageable pageable);
    Page<Post> getDeletedPosts(Pageable pageable);
    void permanentlyDeletePost(Long postId);
    void purgeOldPosts(LocalDateTime threshold);
    
    // System operations
    void processScheduledPosts();
    void cleanupExpiredPolls();
    void updatePostMetrics();
    void generateDailyReports();
    
    // Validation and utilities
    boolean validatePostContent(Post post);
    boolean checkPostPermissions(Long postId, Long userId);
    boolean isPostVisibleToUser(Long postId, Long userId);
    Set<String> extractHashtags(String content);
    Set<Long> extractMentions(String content);
    
    // Cache operations
    void evictPostCache(Long postId);
    void evictUserPostsCache(Long userId);
    void evictTrendingPostsCache();
    
    // Event publishing
    void publishPostCreatedEvent(Post post);
    void publishPostUpdatedEvent(Post post);
    void publishPostDeletedEvent(Long postId);
    void publishPostEngagementEvent(Long postId, String eventType, Long userId);
    
    // Health and monitoring
    Map<String, Object> getServiceHealth();
    Map<String, Object> getPerformanceMetrics();
    Map<String, Object> getUsageStatistics();
    
    // Migration and synchronization
    void migratePostsFromLegacySystem();
    void synchronizeWithExternalSystems();
    void validateDataConsistency();
}
