package ru.lomov.flash.likes.services;

import ru.lomov.flash.likes.dto.LikeResponse;

import java.util.List;

public interface LikeService {
    LikeResponse likePost(String postId, String userId);
    LikeResponse unlikePost(String postId, String userId);
    long getLikesCount(String postId);
    List<LikeResponse> getPostLikes(String postId);
    List<LikeResponse> getUserLikes(String userId);
    boolean isPostLiked(String postId, String userId);
    long getUserLikesCount(String userId);
}
