package ru.lomov.flashbackend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.lomov.flashbackend.entities.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post, String> {

    // Basic queries
    Page<Post> findByUserId(String userId, Pageable pageable);
    Page<Post> findByUserIdAndIsDeletedFalse(String userId, Pageable pageable);
    Page<Post> findByUserIdAndStatus(String userId, String status, Pageable pageable);
    
    // Visibility and status queries
    Page<Post> findByVisibilityAndStatusAndIsDeletedFalse(String visibility, String status, Pageable pageable);
    Page<Post> findByUserIdAndVisibilityAndStatusAndIsDeletedFalse(String userId, String visibility, String status, Pageable pageable);

    // Content search
    @Query("SELECT p FROM Post p WHERE p.content LIKE %:query% AND p.isDeleted = false AND p.status = 'ACTIVE'")
    Page<Post> searchByContent(@Param("query") String query, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.userId IN :userIds AND p.isDeleted = false AND p.status = 'ACTIVE'")
    Page<Post> findByUserIds(@Param("userIds") Set<String> userIds, Pageable pageable);
    
    // Hashtag queries
    @Query("SELECT p FROM Post p JOIN p.hashtags h WHERE h = :hashtag AND p.isDeleted = false AND p.status = 'ACTIVE'")
    Page<Post> findByHashtag(@Param("hashtag") String hashtag, Pageable pageable);
    
    @Query("SELECT p FROM Post p WHERE :hashtag MEMBER OF p.hashtags AND p.isDeleted = false AND p.status = 'ACTIVE'")
    Page<Post> findByHashtagContaining(@Param("hashtag") String hashtag, Pageable pageable);
    
    // Mention queries
    @Query("SELECT p FROM Post p WHERE :userId MEMBER OF p.mentionedUserIds AND p.isDeleted = false AND p.status = 'ACTIVE'")
    Page<Post> findByMentionedUser(@Param("userId") String userId, Pageable pageable);
    
    // Location-based queries
    @Query("SELECT p FROM Post p WHERE p.latitude IS NOT NULL AND p.longitude IS NOT NULL " +
           "AND FUNCTION('ST_Distance_Sphere', FUNCTION('POINT', p.longitude, p.latitude), FUNCTION('POINT', :longitude, :latitude)) <= :radius " +
           "AND p.isDeleted = false AND p.status = 'ACTIVE'")
    Page<Post> findByLocationWithinRadius(@Param("latitude") Double latitude, 
                                         @Param("longitude") Double longitude, 
                                         @Param("radius") Double radius, 
                                         Pageable pageable);
    
    // Time-based queries
    Page<Post> findByCreatedAtAfter(LocalDateTime date, Pageable pageable);
    Page<Post> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);
    
    // Analytics queries
    @Query("SELECT p FROM Post p WHERE p.userId = :userId AND p.isDeleted = false ORDER BY p.likeCount DESC")
    Page<Post> findTopByUserIdOrderByLikeCountDesc(@Param("userId") String userId, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.userId = :userId AND p.isDeleted = false ORDER BY p.commentCount DESC")
    Page<Post> findTopByUserIdOrderByCommentCountDesc(@Param("userId") String userId, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.userId = :userId AND p.isDeleted = false ORDER BY p.viewCount DESC")
    Page<Post> findTopByUserIdOrderByViewCountDesc(@Param("userId") String userId, Pageable pageable);
    
    // Popular posts
    @Query("SELECT p FROM Post p WHERE p.isDeleted = false AND p.status = 'ACTIVE' " +
           "ORDER BY (p.likeCount * 0.4 + p.commentCount * 0.3 + p.viewCount * 0.2 + p.shareCount * 0.1) DESC")
    Page<Post> findPopularPosts(Pageable pageable);
    
    // Trending posts (last 24 hours)
    @Query("SELECT p FROM Post p WHERE p.createdAt >= :since AND p.isDeleted = false AND p.status = 'ACTIVE' " +
           "ORDER BY (p.likeCount * 0.4 + p.commentCount * 0.3 + p.viewCount * 0.2 + p.shareCount * 0.1) DESC")
    Page<Post> findTrendingPosts(@Param("since") LocalDateTime since, Pageable pageable);
    
    // Scheduled posts
    Page<Post> findByScheduledAtIsNotNullAndPublishedAtIsNull(Pageable pageable);
    List<Post> findByScheduledAtBeforeAndPublishedAtIsNull(LocalDateTime now);
    
    // Live posts
    Page<Post> findByIsLiveTrue(Pageable pageable);
    List<Post> findByIsLiveTrueAndLiveStartedAtBefore(LocalDateTime threshold);
    
    // Poll posts
    Page<Post> findByPostTypeAndPollEndsAtAfter(String postType, LocalDateTime now, Pageable pageable);
    Page<Post> findByPostTypeAndPollEndsAtBefore(String postType, LocalDateTime now, Pageable pageable);
    
    // Sponsored content
    Page<Post> findByIsSponsoredTrueAndIsDeletedFalse(Pageable pageable);
    Page<Post> findBySponsorIdAndIsDeletedFalse(String sponsorId, Pageable pageable);
    
    // Moderation queries
    Page<Post> findByIsModeratedFalse(Pageable pageable);
    Page<Post> findByModerationStatus(String status, Pageable pageable);
    Page<Post> findByModerationScoreGreaterThanEqual(Double score, Pageable pageable);
    
    // Archive and deletion queries
    Page<Post> findByIsArchivedTrue(Pageable pageable);
    Page<Post> findByIsDeletedTrue(Pageable pageable);
    
    // Count queries for analytics
    @Query("SELECT COUNT(p) FROM Post p WHERE p.userId = :userId AND p.isDeleted = false")
    Long countByUserId(@Param("userId") String userId);

    @Query("SELECT COUNT(p) FROM Post p WHERE p.userId = :userId AND p.createdAt >= :start AND p.createdAt <= :end AND p.isDeleted = false")
    Long countByUserIdAndDateRange(@Param("userId") String userId,
                                  @Param("start") LocalDateTime start,
                                  @Param("end") LocalDateTime end);

    @Query("SELECT SUM(p.likeCount) FROM Post p WHERE p.userId = :userId AND p.isDeleted = false")
    Long sumLikeCountByUserId(@Param("userId") String userId);

    @Query("SELECT SUM(p.commentCount) FROM Post p WHERE p.userId = :userId AND p.isDeleted = false")
    Long sumCommentCountByUserId(@Param("userId") String userId);

    @Query("SELECT SUM(p.viewCount) FROM Post p WHERE p.userId = :userId AND p.isDeleted = false")
    Long sumViewCountByUserId(@Param("userId") String userId);
    
    // Exists queries
    boolean existsByPostIdAndUserId(String postId, String userId);
    boolean existsByPostIdAndIsDeletedFalse(String postId);

    // Find by multiple IDs
    List<Post> findByPostIdIn(Set<String> postIds);

    // Custom find with all filters
    @Query("SELECT p FROM Post p WHERE " +
           "(:userId IS NULL OR p.userId = :userId) AND " +
           "(:visibility IS NULL OR p.visibility = :visibility) AND " +
           "(:status IS NULL OR p.status = :status) AND " +
           "(:isDeleted IS NULL OR p.isDeleted = :isDeleted) AND " +
           "(:isArchived IS NULL OR p.isArchived = :isArchived) AND " +
           "(:isSponsored IS NULL OR p.isSponsored = :isSponsored) AND " +
           "(:postType IS NULL OR p.postType = :postType) AND " +
           "(:hashtag IS NULL OR :hashtag MEMBER OF p.hashtags) AND " +
           "p.isDeleted = false")
    Page<Post> findByFilters(@Param("userId") String userId,
                            @Param("visibility") String visibility,
                            @Param("status") String status,
                            @Param("isDeleted") Boolean isDeleted,
                            @Param("isArchived") Boolean isArchived,
                            @Param("isSponsored") Boolean isSponsored,
                            @Param("postType") String postType,
                            @Param("hashtag") String hashtag,
                            Pageable pageable);

    Page<Post> findByUserIdAndIsArchivedTrue(String userId, Pageable pageable);
}
