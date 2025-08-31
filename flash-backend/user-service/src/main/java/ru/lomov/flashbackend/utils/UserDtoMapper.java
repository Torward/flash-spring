package ru.lomov.flashbackend.utils;

import ru.lomov.flashbackend.dto.UserDto;
import ru.lomov.flashbackend.dto.UserProfileDto;
import ru.lomov.flashbackend.entities.AppUser;

public class UserDtoMapper {

    public static UserDto userToDto(AppUser user) {
        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName()); // Use name instead of firstName/lastName
        dto.setUsername(user.getUsername());
        dto.setProfilePicture(user.getProfilePicture());
        dto.setCover(user.getCover());
        dto.setBio(user.getBio());
        dto.setWebsite(user.getWebsite());
        dto.setLocation(user.getLocation());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setStatus(user.getStatus());
        dto.setPrivate(user.getIsPrivate()); // Use setPrivate() for boolean field
        dto.setVerified(user.isVerified());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setFollowerCount(user.getFollowerCount());
        dto.setFollowingCount(user.getFollowingCount());
        dto.setPostCount(user.getPostCount());
        dto.setNotificationCount(user.getNotificationCount());
        
        // New fields for Firebase compliance
        dto.setBlockedUsers(user.getBlockedUsers());
        dto.setHighStories(user.getHighStories());
        dto.setNotifications(user.getNotifications());
        
        return dto;
    }

    public static UserProfileDto userToProfileDto(AppUser user) {
        UserProfileDto dto = new UserProfileDto();
        dto.setUserId(user.getUserId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getName()); // Use name instead of firstName/lastName
        dto.setUsername(user.getUsername());
        dto.setProfilePicture(user.getProfilePicture());
        dto.setCover(user.getCover());
        dto.setBio(user.getBio());
        dto.setWebsite(user.getWebsite());
        dto.setLocation(user.getLocation());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setFollowersCount(user.getFollowerCount());
        dto.setFollowingCount(user.getFollowingCount());
        dto.setPostsCount(user.getPostCount());
        dto.setPrivate(user.getIsPrivate()); // Use setPrivate() for boolean field
        dto.setVerified(user.isVerified());
        return dto;
    }
}
