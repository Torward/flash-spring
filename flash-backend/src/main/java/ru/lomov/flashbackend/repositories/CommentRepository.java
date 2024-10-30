package ru.lomov.flashbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.Comment;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c JOIN FETCH c.status WHERE c.status.id = :statusId")
    List<Comment> findAllByStatusId(@Param("statusId") Long statusId);

    @Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.user.userId = :userId")
    List<Comment> findAllByUser_UserId(@Param("userId") Long userId);

    @Query("SELECT c FROM Comment c JOIN FETCH c.post WHERE c.post.postId = :postId")
    List<Comment> findAllByPostId(@Param("postId") Long postId);

    @Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.user = :author")
    List<Comment> findByAuthor(@Param("author") AppUser user);

    List<Comment> findByContentContains(String content);

    @Query("SELECT c FROM Comment c WHERE SIZE(c.likes) > :likesCount")
    List<Comment> findByLikesCountGreaterThan(@Param("likesCount") int likesCount);

    @Query("SELECT c FROM Comment c WHERE SIZE(c.replyComments) > :replyCount")
    List<Comment> findByReplyCommentsCountGreaterThan(@Param("replyCount") int replyCount);

    @Query("SELECT c FROM Comment c WHERE SIZE(c.likes) < :likesCount")
    List<Comment> findByLikesCountLessThan(@Param("likesCount") int likesCount);

    @Query("SELECT c FROM Comment c WHERE SIZE(c.replyComments) < :replyCount")
    List<Comment> findByReplyCommentsCountLessThan(@Param("replyCount") int replyCount);

    @Query("SELECT c FROM Comment c WHERE SIZE(c.likes) = :likesCount")
    List<Comment> findByLikesCount(@Param("likesCount") int likesCount);

    @Query("SELECT c FROM Comment c WHERE SIZE(c.replyComments) = :replyCount")
    List<Comment> findByReplyCommentsCount(@Param("replyCount") int replyCount);

    @Query("SELECT c FROM Comment c WHERE c.post.postId = :postId")
    List<Comment> findByPostId(@Param("postId") Long postId);

    List<Comment> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Comment> findByModifiedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    @Query("SELECT c FROM Comment c WHERE c.post.postId = :postId AND c.deletedAt IS NULL")
    List<Comment> findByPostIdAndDeletedAtIsNull(Long postId);
    @Query("SELECT c FROM Comment c WHERE c.deletedAt BETWEEN :startDate AND :endDate")
    List<Comment> findByDeletedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Comment> findByCreatedAtBefore(LocalDateTime date);
    List<Comment> findByModifiedAtBefore(LocalDateTime date);
    List<Comment> findByDeletedAtBefore(LocalDateTime date);
    List<Comment> findByCreatedAtAfter(LocalDateTime date);
    List<Comment> findByModifiedAtAfter(LocalDateTime date);
    List<Comment> findByDeletedAtAfter(LocalDateTime date);
    @Query("SELECT c FROM Comment c WHERE c.post.postId = :postId AND c.deletedAt BETWEEN :startDate AND :endDate")
    List<Comment> findByPostIdAndDeletedAtBetween(Long postId, LocalDateTime startDate, LocalDateTime endDate);
    @Query("SELECT c FROM Comment c WHERE c.post.postId = :postId AND c.deletedAt BETWEEN :startDate AND :endDate AND c.modifiedAt BETWEEN :startDate1 AND :endDate1")
    List<Comment> findByPostIdAndDeletedAtBetweenAndModifiedAtBetween(Long postId, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime startDate1, LocalDateTime endDate1);
}
