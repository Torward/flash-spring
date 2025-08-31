package ru.lomov.flash.likes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.lomov.flash.likes.entities.Like;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, String> {
    
    Optional<Like> findByPostIdAndUserId(String postId, String userId);
    
    List<Like> findByPostId(String postId);
    
    List<Like> findByUserId(String userId);
    
    long countByPostId(String postId);
    
    long countByUserId(String userId);
    
    boolean existsByPostIdAndUserId(String postId, String userId);
    
    @Query("SELECT l FROM Like l WHERE l.postId = :postId AND l.userId = :userId AND l.isActive = true")
    Optional<Like> findActiveLike(@Param("postId") String postId, @Param("userId") String userId);
    
    @Query("SELECT COUNT(l) FROM Like l WHERE l.postId = :postId AND l.isActive = true")
    long countActiveLikesByPostId(@Param("postId") String postId);
    
    @Query("SELECT COUNT(l) FROM Like l WHERE l.userId = :userId AND l.isActive = true")
    long countActiveLikesByUserId(@Param("userId") String userId);
    
    @Query("SELECT l FROM Like l WHERE l.postId = :postId AND l.isActive = true")
    List<Like> findActiveLikesByPostId(@Param("postId") String postId);
    
    @Query("SELECT l FROM Like l WHERE l.userId = :userId AND l.isActive = true")
    List<Like> findActiveLikesByUserId(@Param("userId") String userId);
    
    @Query("SELECT l FROM Like l WHERE l.postId = :postId AND l.userId = :userId AND l.isActive = true")
    boolean isPostLiked(@Param("postId") String postId, @Param("userId") String userId);
}
