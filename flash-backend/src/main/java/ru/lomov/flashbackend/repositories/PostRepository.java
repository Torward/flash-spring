
package ru.lomov.flashbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.Post;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p JOIN FETCH p.appUser WHERE p.isPost = true ORDER BY p.createdAt ASC")
    List<Post> findAllByIsPostTrueOrderByCreatedAtAsc();

    @Query("SELECT p FROM Post p WHERE :appUser MEMBER OF p.repostAppUser OR p.appUser.userId = :userId AND p.isPost = true ORDER BY p.createdAt DESC")
    List<Post> findByRepostAppUserContainsOrAppUser_UserIdAndIsPostTrueOrderByCreatedAtDesc(@Param("appUser") AppUser appUser, @Param("userId") Long userId);

    List<Post> findByAppUserOrderByCreatedAtDesc(AppUser appUser);
    List<Post> findByContentContaining(String content);

    @Query("SELECT p FROM Post p WHERE p.appUser.userId = :userId")
    List<Post> findByAppUser_UserId(@Param("userId") Long userId);

    @Query("SELECT p FROM Post p JOIN p.likes l WHERE l.appUser.userId = :userId")
    List<Post> findByAppLikesAndAppUser_UserId(@Param("userId") Long userId);

    List<Post> findByCreatedAtAfter(LocalDateTime date);
    List<Post> findByCreatedAtBefore(LocalDateTime date);
    List<Post> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT p FROM Post p WHERE SIZE(p.likes) = :likesCount")
    List<Post> findByLikesCount(@Param("likesCount") int likesCount);

    @Query("SELECT p FROM Post p WHERE SIZE(p.likes) > :likesCount")
    List<Post> findByLikesCountGreaterThan(@Param("likesCount") int likesCount);

    @Query("SELECT p FROM Post p WHERE SIZE(p.likes) < :likesCount")
    List<Post> findByLikesCountLessThan(@Param("likesCount") int likesCount);

    @Query("SELECT p FROM Post p WHERE SIZE(p.comments) = :commentsCount")
    List<Post> findByCommentsCount(@Param("commentsCount") int commentsCount);

    @Query("SELECT p FROM Post p WHERE SIZE(p.comments) > :commentsCount")
    List<Post> findByCommentsCountGreaterThan(@Param("commentsCount") int commentsCount);

    @Query("SELECT p FROM Post p WHERE SIZE(p.comments) < :commentsCount")
    List<Post> findByCommentsCountLessThan(@Param("commentsCount") int commentsCount);

    @Query("SELECT p FROM Post p WHERE SIZE(p.shares) = :sharesCount")
    List<Post> findBySharesCount(@Param("sharesCount") int sharesCount);

    @Query("SELECT p FROM Post p WHERE SIZE(p.shares) > :sharesCount")
    List<Post> findBySharesCountGreaterThan(@Param("sharesCount") int sharesCount);

    @Query("SELECT p FROM Post p WHERE SIZE(p.shares) < :sharesCount")
    List<Post> findBySharesCountLessThan(@Param("sharesCount") int sharesCount);

    List<Post> findByImage(String imageUrl);
    List<Post> findByVideo(String video);

    List<Post> findByCreatedAtAfterAndIsPostTrue(LocalDateTime date);
    List<Post> findByCreatedAtBeforeAndIsPostTrue(LocalDateTime date);
    List<Post> findByCreatedAtBetweenAndIsPostTrue(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT p FROM Post p WHERE p.appUser = :user AND p.content = :content")
    List<Post> findByUserAndContent(@Param("user") AppUser user, @Param("content") String content);

    @Query("SELECT p FROM Post p WHERE p.appUser = :user AND p.video = :video")
    List<Post> findByUserAndVideo(@Param("user") AppUser user, @Param("video") String video);

    @Query("SELECT p FROM Post p WHERE p.appUser = :user AND p.image = :imageUrl")
    List<Post> findByUserAndImageUrl(@Param("user") AppUser user, @Param("imageUrl") String imageUrl);

    @Query("SELECT p FROM Post p WHERE p.appUser = :user AND p.createdAt = :createdAt")
    List<Post> findByUserAndCreatedAt(@Param("user") AppUser user, @Param("createdAt") LocalDateTime createdAt);

    @Query("SELECT p FROM Post p WHERE p.appUser = :user AND p.content = :content AND p.image = :imageUrl")
    List<Post> findByUserAndContentAndImageUrl(@Param("user") AppUser user, @Param("content") String content, @Param("imageUrl") String imageUrl);

    @Query("SELECT p FROM Post p WHERE p.appUser = :user AND p.content = :content AND p.createdAt = :createdAt")
    List<Post> findByUserAndContentAndCreatedAt(@Param("user") AppUser user, @Param("content") String content, @Param("createdAt") LocalDateTime createdAt);

    @Query("SELECT p FROM Post p WHERE p.appUser = :user AND p.content = :content AND p.image = :imageUrl AND p.createdAt = :createdAt")
    List<Post> findByUserAndImageUrlAndCreatedAt(@Param("user") AppUser user, @Param("imageUrl") String imageUrl, @Param("createdAt") LocalDateTime createdAt);

    List<Post> findByContentAndImageAndCreatedAt(String content, String imageUrl, LocalDateTime createdAt);
    List<Post> findByAppUserAndContentAndImageAndCreatedAt(AppUser user, String content, String imageUrl, LocalDateTime createdAt);

    @Query("SELECT p FROM Post p JOIN p.replyFor r WHERE r = :user")
    List<Post> findPostsByRepliesForContainsAppUser(@Param("user") AppUser user);
}