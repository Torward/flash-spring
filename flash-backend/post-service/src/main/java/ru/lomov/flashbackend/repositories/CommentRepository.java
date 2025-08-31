package ru.lomov.flashbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lomov.flashbackend.entities.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
    List<Comment> findByPostIdAndIsDeletedFalse(Long postId);
    List<Comment> findByUserId(Long userId);
    List<Comment> findByParentCommentId(Long parentCommentId);
    Optional<Comment> findByCommentIdAndIsDeletedFalse(Long commentId);
    long countByPostId(Long postId);
    long countByPostIdAndIsDeletedFalse(Long postId);
    
    @Query("SELECT c FROM Comment c WHERE c.postId = :postId AND c.parentCommentId IS NULL ORDER BY c.createdAt DESC")
    List<Comment> findTopLevelCommentsByPostId(@Param("postId") Long postId);

    Long countByUserId(Long userId);
}
