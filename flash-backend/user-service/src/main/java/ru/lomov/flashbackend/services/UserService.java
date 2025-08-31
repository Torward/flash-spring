package ru.lomov.flashbackend.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.exceptions.UserNotFoundException;
import ru.lomov.flashbackend.dto.UserDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {
    AppUser findUserById(String userId) throws UserNotFoundException;
    AppUser findUserByEmail(String email) throws UserNotFoundException;
    AppUser createUser(AppUser user);
    AppUser updateUser(String userId, AppUser user) throws UserNotFoundException;
    void deleteUser(String userId, AppUser reqUser) throws UserNotFoundException;
    void followUser(String reqUserId, String userId) throws UserNotFoundException;
    void unfollowUser(String reqUserId, String userId) throws UserNotFoundException;
    
    // Missing methods that are called in UserController
    Page<AppUser> searchUsers(String query, Pageable pageable) throws UserNotFoundException;
    Page<AppUser> getUserFollowers(String userId, Pageable pageable) throws UserNotFoundException;
    Page<AppUser> getUserFollowing(String userId, Pageable pageable) throws UserNotFoundException;
    AppUser updateUserInterests(String userId, Set<String> interests) throws UserNotFoundException;
    AppUser updateUserSkills(String userId, Set<String> skills) throws UserNotFoundException;
    AppUser updateUserSettings(String userId, Map<String, Object> settings) throws UserNotFoundException;
    List<AppUser> getRecommendedUsers(String userId, int limit) throws UserNotFoundException;
    Map<String, Object> getUserStats(String userId) throws UserNotFoundException;

    // New Firebase-compatible methods
    Set<String> getBlockedUsers(String userId) throws UserNotFoundException;
    void blockUser(String userId, String blockedUserId) throws UserNotFoundException;
    void unblockUser(String userId, String blockedUserId) throws UserNotFoundException;
    Set<String> getNotifications(String userId) throws UserNotFoundException;
    void addNotification(String userId, String notificationId) throws UserNotFoundException;
    void removeNotification(String userId, String notificationId) throws UserNotFoundException;
    void updateStatus(String userId, String status) throws UserNotFoundException;
    Set<String> getHighStories(String userId) throws UserNotFoundException;
    void addHighStory(String userId, String storyId) throws UserNotFoundException;
    void removeHighStory(String userId, String storyId) throws UserNotFoundException;
}
