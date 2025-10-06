package ru.lomov.flashbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lomov.flashbackend.entities.Like;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, String> {
    List<Like> findByPostId(String postId);
    List<Like> findByUserId(String userId);
    Optional<Like> findByPostIdAndUserId(String postId, String userId);
    long countByPostId(String postId);
    long countByUserId(String userId);
    boolean existsByPostIdAndUserId(String postId, String userId);

    @Query("SELECT l FROM Like l WHERE l.postId = :postId AND l.reactionType = :reactionType")
    List<Like> findByPostIdAndReactionType(@Param("postId") String postId, @Param("reactionType") String reactionType);

    @Query("SELECT COUNT(l) FROM Like l WHERE l.postId = :postId AND l.reactionType = :reactionType")
    long countByPostIdAndReactionType(@Param("postId") String postId, @Param("reactionType") String reactionType);

    @Query("SELECT l.postId, COUNT(l) FROM Like l WHERE l.postId IN :postIds GROUP BY l.postId")
    List<Object[]> countLikesByPostIds(@Param("postIds") List<String> postIds);
}
