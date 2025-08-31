package ru.lomov.flashbackend.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserProfileDto {
    private String userId;
    private String email;
    private String name; // Combined first and last name for Firebase compatibility
    private String username;
    private String profilePicture;
    private String cover;
    private String bio;
    private String website;
    private String location;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int followersCount;
    private int followingCount;
    private int postsCount;
    private boolean isPrivate;
    private boolean isVerified;
    private boolean isReqUser;
    private boolean isFollowing;
    private boolean isFollowedBy;
}
