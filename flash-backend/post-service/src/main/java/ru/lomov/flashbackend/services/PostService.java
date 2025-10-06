package ru.lomov.flashbackend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.lomov.flashbackend.entities.Post;
import ru.lomov.flashbackend.exceptions.PostNotFoundException;
import ru.lomov.flashbackend.dto.UpdatePostTypeDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PostService {
    
    // Basic CRUD operations
    Post createPost(Post post);
    Post getPostById(String postId) throws PostNotFoundException;
    Post updatePost(String postId, Post post) throws PostNotFoundException;
    Post updatePostType(String postId, UpdatePostTypeDto updatePostTypeDto) throws PostNotFoundException;
    void deletePost(String postId, String userId) throws PostNotFoundException;
    void archivePost(String postId, String userId) throws PostNotFoundException;
    void restorePost(String postId, String userId) throws PostNotFoundException;

    // User posts
    Page<Post> getUserPosts(String userId, Pageable pageable);
    Page<Post> getUserPublicPosts(String userId, Pageable pageable);
    Page<Post> getUserDraftPosts(String userId, Pageable pageable);
    Page<Post> getUserArchivedPosts(String userId, Pageable pageable);

    // Feed and discovery
    Page<Post> getFeedPosts(String userId, Pageable pageable);
    Page<Post> getDiscoverPosts(Pageable pageable);
    Page<Post> getTrendingPosts(Pageable pageable);
    Page<Post> getPopularPosts(Pageable pageable);
    
    // Search and filtering
    Page<Post> searchPosts(String query, Pageable pageable);
    Page<Post> searchPostsByHashtag(String hashtag, Pageable pageable);
    Page<Post> searchPostsByLocation(Double latitude, Double longitude, Double radius, Pageable pageable);
    Page<Post> searchPostsByMention(String mentionedUserId, Pageable pageable);

    // Content moderation
    Page<Post> getPostsForModeration(Pageable pageable);
    Post moderatePost(String postId, String status, String reason, Double score) throws PostNotFoundException;
    Page<Post> getModeratedPosts(String status, Pageable pageable);

    // Analytics and insights
    Map<String, Object> getUserPostStats(String userId);
    Map<String, Object> getPostAnalytics(String postId) throws PostNotFoundException;
    Map<String, Object> getTrendingHashtags(int limit);
    Map<String, Object> getLocationInsights(Double latitude, Double longitude, Double radius);
    
    // Engagement operations
    Post likePost(String postId, String userId) throws PostNotFoundException;
    Post unlikePost(String postId, String userId) throws PostNotFoundException;
    Post viewPost(String postId, String userId) throws PostNotFoundException;
    Post savePost(String postId, String userId) throws PostNotFoundException;
    Post unsavePost(String postId, String userId) throws PostNotFoundException;
    Post sharePost(String postId, String userId) throws PostNotFoundException;

    // Comments management (delegated to comment service)
    Post addComment(String postId, String userId, String comment) throws PostNotFoundException;
    Post removeComment(String postId, String commentId, String userId) throws PostNotFoundException;

    // Poll operations
    Post voteInPoll(String postId, String userId, int optionIndex) throws PostNotFoundException;
    Post createPoll(String userId, String question, List<String> options, LocalDateTime endsAt);
    Post closePoll(String postId, String userId) throws PostNotFoundException;

    // Live stream operations
    Post startLiveStream(String userId, String title, String description);
    Post endLiveStream(String postId, String userId) throws PostNotFoundException;
    Post updateLiveViewers(String postId, int viewerCount) throws PostNotFoundException;

    // Scheduled posts
    Page<Post> getScheduledPosts(String userId, Pageable pageable);
    Post schedulePost(Post post, LocalDateTime scheduleTime);
    Post publishScheduledPost(String postId) throws PostNotFoundException;
    void cancelScheduledPost(String postId, String userId) throws PostNotFoundException;
    
    // Sponsored content
    Page<Post> getSponsoredPosts(Pageable pageable);
    Post createSponsoredPost(Post post, String sponsorId, String campaignId);
    Post updateSponsorship(String postId, String sponsorId, String campaignId, Double cpmRate) throws PostNotFoundException;

    // Bulk operations
    List<Post> bulkCreatePosts(List<Post> posts);
    void bulkDeletePosts(List<String> postIds, String userId);
    void bulkArchivePosts(List<String> postIds, String userId);
    void bulkPublishPosts(List<String> postIds);

    // Export and backup
    String exportUserPosts(String userId, String format);
    void backupPosts(LocalDateTime beforeDate);
    void restorePostsFromBackup(String backupId);

    // Content recommendations
    Page<Post> getRecommendedPosts(String userId, Pageable pageable);
    Page<Post> getSimilarPosts(String postId, Pageable pageable);
    Page<Post> getPostsYouMayLike(String userId, Pageable pageable);

    // Social features
    Page<Post> getPostsFromFollowing(String userId, Pageable pageable);
    Page<Post> getPostsLikedByUser(String userId, Pageable pageable);
    Page<Post> getPostsSavedByUser(String userId, Pageable pageable);
    Page<Post> getPostsSharedByUser(String userId, Pageable pageable);

    // Administrative operations
    Page<Post> getAllPosts(Pageable pageable);
    Page<Post> getDeletedPosts(Pageable pageable);
    void permanentlyDeletePost(String postId);
    void purgeOldPosts(LocalDateTime threshold);
    
    // System operations
    void processScheduledPosts();
    void cleanupExpiredPolls();
    void updatePostMetrics();
    void generateDailyReports();
    
    // Validation and utilities
    boolean validatePostContent(Post post);
    boolean checkPostPermissions(String postId, String userId);
    boolean isPostVisibleToUser(String postId, String userId);
    Set<String> extractHashtags(String content);
    Set<String> extractMentions(String content);

    // Cache operations
    void evictPostCache(String postId);
    void evictUserPostsCache(String userId);
    void evictTrendingPostsCache();

    // Event publishing
    void publishPostCreatedEvent(Post post);
    void publishPostUpdatedEvent(Post post);
    void publishPostDeletedEvent(String postId);
    void publishPostEngagementEvent(String postId, String eventType, String userId);
    
    // Health and monitoring
    Map<String, Object> getServiceHealth();
    Map<String, Object> getPerformanceMetrics();
    Map<String, Object> getUsageStatistics();
    
    // Migration and synchronization
    void migratePostsFromLegacySystem();
    void synchronizeWithExternalSystems();
    void validateDataConsistency();
}
