package ru.lomov.flashbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lomov.flashbackend.entities.AppLike;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.Post;
import ru.lomov.flashbackend.entities.Share;

import java.util.List;

@Repository
public interface ShareRepository extends JpaRepository<Share, Long> {
    @Query("select s from Share s where s.appUser.userId=:userId and  s.post.postId=:postId")
    Share isShareExist(@Param("userId") Long userId, @Param("postId") Long postId);

    @Query("select s from Share s where s.post.postId=:postId")
    List<Share> findAllByPostId(@Param("postId") Long postId);

    List<Share> findByAppUser(AppUser user);

    List<Share> findByPost(Post post);

    Share findByAppUserAndPost(AppUser user, Post post);

    boolean existsByAppUserAndPost(AppUser user, Post post);

    long countByPost(Post post);
}