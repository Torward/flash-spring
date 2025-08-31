package ru.lomov.flashbackend.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserDto {
    private String userId;
    private String email;
    private String name; // Combined first and last name for Firebase compatibility
    private String username;
    private String profilePicture;
    private String cover;
    private String bio;
    private String website;
    private String location;
    private String phoneNumber;
    private String status;
    private boolean isPrivate;
    private boolean isVerified; // Add setter for this field
    private LocalDateTime createdAt;
    private int followerCount;
    private int followingCount;
    private int postCount;
    private int notificationCount;
    private boolean req_user;
    private boolean followed;

    // New fields for Firebase compliance
    private Set<String> blockedUsers; // Blocked users
    private Set<String> highStories; // Favorite stories
    private Set<String> notifications; // Notifications

    // Explicit setters for Lombok compatibility
    public void setStatus(String status) {
        this.status = status;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setNotifications(Set<String> notifications) {
        this.notifications = notifications;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setNotificationCount(int notificationCount) {
        this.notificationCount = notificationCount;
    }

    public void setBlockedUsers(Set<String> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setVerified(boolean isVerified) {
        this.isVerified = isVerified;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public void setHighStories(Set<String> highStories) {
        this.highStories = highStories;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }
}
