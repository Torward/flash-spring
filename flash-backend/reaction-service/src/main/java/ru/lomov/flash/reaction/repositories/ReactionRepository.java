package ru.lomov.flash.reaction.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.lomov.flash.reaction.entities.Reaction;

import java.util.List;
import java.util.Optional;

public interface ReactionRepository extends JpaRepository<Reaction, String> {
    
    Optional<Reaction> findByPostIdAndUserId(String postId, String userId);
    
    List<Reaction> findByPostId(String postId);
    
    List<Reaction> findByUserId(String userId);
    
    List<Reaction> findByPostIdAndReactionType(String postId, Reaction.ReactionType reactionType);
    
    long countByPostId(String postId);
    
    long countByUserId(String userId);
    
    long countByPostIdAndReactionType(String postId, Reaction.ReactionType reactionType);
    
    boolean existsByPostIdAndUserId(String postId, String userId);
    
    @Query("SELECT r FROM Reaction r WHERE r.postId = :postId AND r.userId = :userId AND r.isActive = true")
    Optional<Reaction> findActiveReaction(@Param("postId") String postId, @Param("userId") String userId);
    
    @Query("SELECT COUNT(r) FROM Reaction r WHERE r.postId = :postId AND r.isActive = true")
    long countActiveReactionsByPostId(@Param("postId") String postId);
    
    @Query("SELECT COUNT(r) FROM Reaction r WHERE r.userId = :userId AND r.isActive = true")
    long countActiveReactionsByUserId(@Param("userId") String userId);
    
    @Query("SELECT r FROM Reaction r WHERE r.postId = :postId AND r.isActive = true")
    List<Reaction> findActiveReactionsByPostId(@Param("postId") String postId);
    
    @Query("SELECT r FROM Reaction r WHERE r.userId = :userId AND r.isActive = true")
    List<Reaction> findActiveReactionsByUserId(@Param("userId") String userId);
    
    @Query("SELECT r.reactionType FROM Reaction r WHERE r.postId = :postId AND r.userId = :userId AND r.isActive = true")
    Optional<Reaction.ReactionType> findActiveReactionType(@Param("postId") String postId, @Param("userId") String userId);
}
