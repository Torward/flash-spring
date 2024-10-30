
package ru.lomov.flashbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lomov.flashbackend.entities.AppLike;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.Post;


import java.util.List;
@Repository
public interface LikeRepository extends JpaRepository<AppLike, Long> {
    @Query("select l from AppLike l where l.appUser.userId=:userId and  l.post.postId=:postId")
    AppLike isLikeExist(@Param("userId") Long userId, @Param("postId") Long postId);

    @Query("select l from AppLike l where l.post.postId=:postId")
    List<AppLike> findAllByPostId(@Param("postId") Long postId);

    List<AppLike> findByAppUser(AppUser user);

    List<AppLike> findByPost(Post post);

    AppLike findByAppUserAndPost(AppUser user, Post post);

    boolean existsByAppUserAndPost(AppUser user, Post post);

    long countByPost(Post post);
}