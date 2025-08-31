package ru.lomov.flash.reels.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.lomov.flash.reels.entities.Reel;

@Repository
public interface ReelRepository extends JpaRepository<Reel, String> {
    List<Reel> findByUserId(String userId);
    List<Reel> findByUserIdAndIsActiveTrue(String userId);
    List<Reel> findByIsPublicTrueAndIsActiveTrue();
    
    @Query("SELECT r FROM Reel r WHERE r.userId = :userId AND r.isActive = true ORDER BY r.createdAt DESC")
    List<Reel> findActiveReelsByUser(@Param("userId") String userId);
    
    @Query("SELECT r FROM Reel r WHERE r.isPublic = true AND r.isActive = true ORDER BY r.createdAt DESC")
    List<Reel> findPublicActiveReels();
    
    Optional<Reel> findByIdAndIsActiveTrue(String id);
    Optional<Reel> findByIdAndUserIdAndIsActiveTrue(String id, String userId);
    
    @Query("SELECT r FROM Reel r WHERE r.userId IN :userIds AND r.isPublic = true AND r.isActive = true ORDER BY r.createdAt DESC")
    List<Reel> findReelsByUsers(@Param("userIds") List<String> userIds);
    
    @Query("SELECT r FROM Reel r WHERE LOWER(r.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(r.description) LIKE LOWER(CONCAT('%', :query, '%')) AND r.isPublic = true AND r.isActive = true")
    List<Reel> searchReels(@Param("query") String query);
}
