package ru.lomov.flashbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lomov.flashbackend.entities.Comment;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<ru.lomov.flashbackend.entities.Comment, String> {
    List<ru.lomov.flashbackend.entities.Comment> findByPostId(String postId);
    List<ru.lomov.flashbackend.entities.Comment> findByPostIdAndIsDeletedFalse(String postId);
    List<ru.lomov.flashbackend.entities.Comment> findByUserId(String userId);
    List<ru.lomov.flashbackend.entities.Comment> findByParentCommentId(String parentCommentId);
    Optional<ru.lomov.flashbackend.entities.Comment> findByCommentIdAndIsDeletedFalse(String commentId);
    long countByPostId(String postId);
    long countByPostIdAndIsDeletedFalse(String postId);

    @Query("SELECT c FROM Comment c WHERE c.postId = :postId AND c.parentCommentId IS NULL ORDER BY c.createdAt DESC")
    List<ru.lomov.flashbackend.entities.Comment> findTopLevelCommentsByPostId(@Param("postId") String postId);

    Long countByUserId(String userId);
}
