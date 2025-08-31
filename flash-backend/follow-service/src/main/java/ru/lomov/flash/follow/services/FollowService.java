package ru.lomov.flash.follow.services;

import ru.lomov.flash.follow.dto.FollowResponse;

import java.util.List;

public interface FollowService {
    FollowResponse followUser(String userId, String targetUserId);
    FollowResponse unfollowUser(String userId, String targetUserId);
    List<FollowResponse> getFollowing(String userId);
    List<FollowResponse> getFollowers(String userId);
    long getFollowingCount(String userId);
    long getFollowersCount(String userId);
    boolean isFollowing(String userId, String targetUserId);
    List<FollowResponse> getMutualFollowers(String userId, String targetUserId);
}
