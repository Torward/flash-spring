package ru.lomov.flash.follow.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FollowResponse {
    private String id;
    private String userId;
    private String targetUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isFollowing;
    private boolean isFollowedBack;
    
    // Firebase-compatible fields
    private String followerId;
    private String followingId;
    private int followerCount;
    private int followingCount;
    
    public FollowResponse() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
