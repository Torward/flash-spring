package ru.lomov.flash.follow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.lomov.flash.follow.entities.Follow;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, String> {
    
    Optional<Follow> findByUserIdAndTargetUserId(String userId, String targetUserId);
    
    List<Follow> findByUserId(String userId);
    
    List<Follow> findByTargetUserId(String targetUserId);
    
    long countByUserId(String userId);
    
    long countByTargetUserId(String targetUserId);
    
    boolean existsByUserIdAndTargetUserId(String userId, String targetUserId);
    
    @Query("SELECT f FROM Follow f WHERE f.userId = :userId AND f.targetUserId = :targetUserId AND f.isActive = true")
    Optional<Follow> findActiveFollow(@Param("userId") String userId, @Param("targetUserId") String targetUserId);
    
    @Query("SELECT COUNT(f) FROM Follow f WHERE f.userId = :userId AND f.isActive = true")
    long countActiveFollowing(@Param("userId") String userId);
    
    @Query("SELECT COUNT(f) FROM Follow f WHERE f.targetUserId = :userId AND f.isActive = true")
    long countActiveFollowers(@Param("userId") String userId);
    
    @Query("SELECT f FROM Follow f WHERE f.userId = :userId AND f.isActive = true")
    List<Follow> findActiveFollowing(@Param("userId") String userId);
    
    @Query("SELECT f FROM Follow f WHERE f.targetUserId = :userId AND f.isActive = true")
    List<Follow> findActiveFollowers(@Param("userId") String userId);
    
    @Query("SELECT f FROM Follow f WHERE f.userId = :userId AND f.targetUserId = :targetUserId AND f.isActive = true")
    boolean isActiveFollowing(@Param("userId") String userId, @Param("targetUserId") String targetUserId);
}
