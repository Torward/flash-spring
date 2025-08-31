package ru.lomov.flashbackend.services;

import ru.lomov.flashbackend.entities.Like;

import java.util.List;
import java.util.Optional;

public interface LikeService {
    Like createLike(Long postId, Long userId);
    Like createLike(Long postId, Long userId, String reactionType);
    void deleteLike(Long likeId);
    void deleteLike(Long postId, Long userId);
    Optional<Like> getLikeById(Long likeId);
    Optional<Like> getLikeByPostAndUser(Long postId, Long userId);
    List<Like> getLikesByPostId(Long postId);
    List<Like> getLikesByUserId(Long userId);
    long getLikeCountByPostId(Long postId);
    long getLikeCountByUserId(Long userId);
    boolean hasUserLikedPost(Long postId, Long userId);
    List<Like> getLikesByPostIdAndReactionType(Long postId, String reactionType);
    long getLikeCountByPostIdAndReactionType(Long postId, String reactionType);
}
