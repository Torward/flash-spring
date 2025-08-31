package ru.lomov.flash.follow.mappers;

import ru.lomov.flash.follow.dto.FollowResponse;
import ru.lomov.flash.follow.entities.Follow;

public class FollowMapper {
    
    public static FollowResponse toResponse(Follow follow) {
        FollowResponse response = new FollowResponse();
        response.setId(follow.getId());
        response.setUserId(follow.getUserId());
        response.setTargetUserId(follow.getTargetUserId());
        response.setFollowerId(follow.getFollowerId());
        response.setFollowingId(follow.getFollowingId());
        response.setFollowerCount(follow.getFollowerCount());
        response.setFollowingCount(follow.getFollowingCount());
        response.setFollowing(follow.getIsFollowing());
        response.setFollowedBack(follow.getIsFollowedBack());
        response.setCreatedAt(follow.getCreatedAt());
        response.setUpdatedAt(follow.getUpdatedAt());
        return response;
    }
    
    public static Follow toEntity(FollowResponse response) {
        Follow follow = new Follow();
        follow.setId(response.getId());
        follow.setUserId(response.getUserId());
        follow.setTargetUserId(response.getTargetUserId());
        follow.setFollowerId(response.getFollowerId());
        follow.setFollowingId(response.getFollowingId());
        follow.setFollowerCount(response.getFollowerCount());
        follow.setFollowingCount(response.getFollowingCount());
        follow.setIsFollowing(response.isFollowing());
        follow.setIsFollowedBack(response.isFollowedBack());
        return follow;
    }
}
