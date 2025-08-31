package ru.lomov.flashbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lomov.flashbackend.entities.Like;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findByPostId(Long postId);
    List<Like> findByUserId(Long userId);
    Optional<Like> findByPostIdAndUserId(Long postId, Long userId);
    long countByPostId(Long postId);
    long countByUserId(Long userId);
    boolean existsByPostIdAndUserId(Long postId, Long userId);
    
    @Query("SELECT l FROM Like l WHERE l.postId = :postId AND l.reactionType = :reactionType")
    List<Like> findByPostIdAndReactionType(@Param("postId") Long postId, @Param("reactionType") String reactionType);
    
    @Query("SELECT COUNT(l) FROM Like l WHERE l.postId = :postId AND l.reactionType = :reactionType")
    long countByPostIdAndReactionType(@Param("postId") Long postId, @Param("reactionType") String reactionType);
    
    @Query("SELECT l.postId, COUNT(l) FROM Like l WHERE l.postId IN :postIds GROUP BY l.postId")
    List<Object[]> countLikesByPostIds(@Param("postIds") List<Long> postIds);
}
