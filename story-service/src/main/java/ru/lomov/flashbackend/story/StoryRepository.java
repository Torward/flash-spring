package ru.lomov.flashbackend.story;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryRepository extends JpaRepository<Story, String> {
    
    List<Story> findByUserId(String userId);
    
    List<Story> findByUserIdAndTimeEndAfter(String userId, LocalDateTime currentTime);
    
    @Query("SELECT s FROM Story s WHERE s.userId IN :userIds AND s.timeEnd > :currentTime ORDER BY s.timeStart DESC")
    List<Story> findActiveStoriesByUserIds(@Param("userIds") List<String> userIds, @Param("currentTime") LocalDateTime currentTime);
    
    @Query("SELECT s FROM Story s WHERE s.timeEnd > :currentTime ORDER BY s.views DESC, s.timeStart DESC")
    List<Story> findPopularStories(@Param("currentTime") LocalDateTime currentTime);
    
    long countByUserId(String userId);
    
    void deleteByTimeEndBefore(LocalDateTime currentTime);
    
    // New Firebase-compatible methods
    List<Story> findByUserIdAndIsHighlight(String userId, Boolean isHighlight);
    
    List<Story> findByUserIdAndIsArchived(String userId, Boolean isArchived);
    
    @Query("SELECT s FROM Story s WHERE s.userId = :userId AND s.isHighlight = true ORDER BY s.createdAt DESC")
    List<Story> findHighlightStoriesByUserId(@Param("userId") String userId);
    
    @Query("SELECT s FROM Story s WHERE s.userId = :userId AND s.type = :type ORDER BY s.createdAt DESC")
    List<Story> findByUserIdAndType(@Param("userId") String userId, @Param("type") String type);
    
    @Query("SELECT s FROM Story s WHERE s.location IS NOT NULL AND s.timeEnd > :currentTime ORDER BY s.createdAt DESC")
    List<Story> findStoriesWithLocation(@Param("currentTime") LocalDateTime currentTime);
    
    @Query("SELECT s FROM Story s WHERE s.hashtags LIKE %:hashtag% AND s.timeEnd > :currentTime ORDER BY s.createdAt DESC")
    List<Story> findStoriesByHashtag(@Param("hashtag") String hashtag, @Param("currentTime") LocalDateTime currentTime);
    
    @Query("SELECT s FROM Story s WHERE s.mentions LIKE %:mention% AND s.timeEnd > :currentTime ORDER BY s.createdAt DESC")
    List<Story> findStoriesByMention(@Param("mention") String mention, @Param("currentTime") LocalDateTime currentTime);
}
