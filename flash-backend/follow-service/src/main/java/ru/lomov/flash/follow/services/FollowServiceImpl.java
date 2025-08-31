package ru.lomov.flash.follow.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flash.follow.dto.FollowResponse;
import ru.lomov.flash.follow.entities.Follow;
import ru.lomov.flash.follow.repositories.FollowRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowRepository followRepository;

    @Override
    public FollowResponse followUser(String userId, String targetUserId) {
        Optional<Follow> existingFollow = followRepository.findByUserIdAndTargetUserId(userId, targetUserId);
        
        Follow follow;
        if (existingFollow.isPresent()) {
            follow = existingFollow.get();
            follow.setIsActive(true);
            follow.setIsFollowing(true);
            follow.setUpdatedAt(LocalDateTime.now());
        } else {
            follow = new Follow();
            follow.setUserId(userId);
            follow.setTargetUserId(targetUserId);
            follow.setFollowerId(userId);
            follow.setFollowingId(targetUserId);
            follow.setIsActive(true);
            follow.setIsFollowing(true);
        }
        
        Follow savedFollow = followRepository.save(follow);
        return mapToResponse(savedFollow);
    }

    @Override
    public FollowResponse unfollowUser(String userId, String targetUserId) {
        Optional<Follow> existingFollow = followRepository.findByUserIdAndTargetUserId(userId, targetUserId);
        
        if (existingFollow.isPresent()) {
            Follow follow = existingFollow.get();
            follow.setIsActive(false);
            follow.setIsFollowing(false);
            follow.setUpdatedAt(LocalDateTime.now());
            
            Follow savedFollow = followRepository.save(follow);
            return mapToResponse(savedFollow);
        }
        
        // Return empty response if not following
        FollowResponse response = new FollowResponse();
        response.setUserId(userId);
        response.setTargetUserId(targetUserId);
        response.setFollowing(false);
        return response;
    }

    @Override
    public List<FollowResponse> getFollowing(String userId) {
        List<Follow> following = followRepository.findActiveFollowing(userId);
        return following.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<FollowResponse> getFollowers(String userId) {
        List<Follow> followers = followRepository.findActiveFollowers(userId);
        return followers.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public long getFollowingCount(String userId) {
        return followRepository.countActiveFollowing(userId);
    }

    @Override
    public long getFollowersCount(String userId) {
        return followRepository.countActiveFollowers(userId);
    }

    @Override
    public boolean isFollowing(String userId, String targetUserId) {
        return followRepository.isActiveFollowing(userId, targetUserId);
    }

    @Override
    public List<FollowResponse> getMutualFollowers(String userId, String targetUserId) {
        List<Follow> userFollowers = followRepository.findActiveFollowers(userId);
        List<Follow> targetFollowers = followRepository.findActiveFollowers(targetUserId);
        
        // Find mutual followers
        return userFollowers.stream()
                .filter(userFollow -> targetFollowers.stream()
                        .anyMatch(targetFollow -> targetFollow.getUserId().equals(userFollow.getUserId())))
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private FollowResponse mapToResponse(Follow follow) {
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
}
