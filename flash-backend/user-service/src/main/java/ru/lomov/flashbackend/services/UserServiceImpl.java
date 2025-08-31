package ru.lomov.flashbackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.exceptions.UserNotFoundException;
import ru.lomov.flashbackend.repositories.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public AppUser findUserById(String userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
    }

    @Override
    public AppUser findUserByEmail(String email) throws UserNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    @Override
    public AppUser createUser(AppUser user) {
        return userRepository.save(user);
    }

    @Override
    public AppUser updateUser(String userId, AppUser user) throws UserNotFoundException {
        AppUser existingUser = findUserById(userId);
        if (user.getName() != null) {
            existingUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getPassword() != null) {
            existingUser.setPassword(user.getPassword());
        }
        if (user.getUsername() != null) {
            existingUser.setUsername(user.getUsername());
        }
        if (user.getProfilePicture() != null) {
            existingUser.setProfilePicture(user.getProfilePicture());
        }
        if (user.getCover() != null) {
            existingUser.setCover(user.getCover());
        }
        if (user.getBio() != null) {
            existingUser.setBio(user.getBio());
        }
        if (user.getWebsite() != null) {
            existingUser.setWebsite(user.getWebsite());
        }
        if (user.getLocation() != null) {
            existingUser.setLocation(user.getLocation());
        }
        if (user.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(user.getPhoneNumber());
        }
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(String userId, AppUser reqUser) throws UserNotFoundException {
        AppUser user = findUserById(userId);
        userRepository.delete(user);
    }

    @Override
    public void followUser(String reqUserId, String userId) throws UserNotFoundException {
        AppUser reqUser = findUserById(reqUserId);
        AppUser userToFollow = findUserById(userId);
        
        reqUser.getFollowing().add(userToFollow.getId());
        userRepository.save(reqUser);
    }

    @Override
    public void unfollowUser(String reqUserId, String userId) throws UserNotFoundException {
        AppUser reqUser = findUserById(reqUserId);
        AppUser userToUnfollow = findUserById(userId);
        
        reqUser.getFollowing().remove(userToUnfollow.getId());
        userRepository.save(reqUser);
    }

    @Override
    public Page<AppUser> searchUsers(String query, Pageable pageable) throws UserNotFoundException {
        return userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                query, query, pageable);
    }

    @Override
    public Page<AppUser> getUserFollowers(String userId, Pageable pageable) throws UserNotFoundException {
        return userRepository.findFollowersByUserId(userId, pageable);
    }

    @Override
    public Page<AppUser> getUserFollowing(String userId, Pageable pageable) throws UserNotFoundException {
        return userRepository.findFollowingByUserId(userId, pageable);
    }

    @Override
    public AppUser updateUserInterests(String userId, Set<String> interests) throws UserNotFoundException {
        AppUser user = findUserById(userId);
        user.setInterests(interests);
        return userRepository.save(user);
    }

    @Override
    public AppUser updateUserSkills(String userId, Set<String> skills) throws UserNotFoundException {
        AppUser user = findUserById(userId);
        user.setSkills(skills);
        return userRepository.save(user);
    }

    @Override
    public AppUser updateUserSettings(String userId, Map<String, Object> settings) throws UserNotFoundException {
        AppUser user = findUserById(userId);
        
        if (settings.containsKey("language")) {
            user.setLanguage((String) settings.get("language"));
        }
        if (settings.containsKey("timezone")) {
            user.setTimezone((String) settings.get("timezone"));
        }
        
        return userRepository.save(user);
    }

    @Override
    public List<AppUser> getRecommendedUsers(String userId, int limit) throws UserNotFoundException {
        AppUser currentUser = findUserById(userId);
        List<AppUser> allUsers = userRepository.findAll();
        
        return allUsers.stream()
                .filter(user -> !user.getId().equals(userId))
                .filter(user -> !currentUser.getFollowing().contains(user.getId()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getUserStats(String userId) throws UserNotFoundException {
        AppUser user = findUserById(userId);
        
        Map<String, Object> stats = new HashMap<>();
        stats.put("userId", user.getId());
        stats.put("postCount", user.getPostCount());
        stats.put("followerCount", user.getFollowerCount());
        stats.put("followingCount", user.getFollowingCount());
        
        return stats;
    }

    @Override
    public Set<String> getBlockedUsers(String userId) throws UserNotFoundException {
        AppUser user = findUserById(userId);
        return user.getBlockedUsers();
    }

    @Override
    public void blockUser(String userId, String blockedUserId) throws UserNotFoundException {
        AppUser user = findUserById(userId);
        user.getBlockedUsers().add(blockedUserId);
        userRepository.save(user);
    }

    @Override
    public void unblockUser(String userId, String blockedUserId) throws UserNotFoundException {
        AppUser user = findUserById(userId);
        user.getBlockedUsers().remove(blockedUserId);
        userRepository.save(user);
    }

    @Override
    public Set<String> getNotifications(String userId) throws UserNotFoundException {
        AppUser user = findUserById(userId);
        return user.getNotifications();
    }

    @Override
    public void addNotification(String userId, String notificationId) throws UserNotFoundException {
        AppUser user = findUserById(userId);
        user.getNotifications().add(notificationId);
        user.setCount(user.getCount() + 1);
        userRepository.save(user);
    }

    @Override
    public void removeNotification(String userId, String notificationId) throws UserNotFoundException {
        AppUser user = findUserById(userId);
        user.getNotifications().remove(notificationId);
        user.setCount(Math.max(0, user.getCount() - 1));
        userRepository.save(user);
    }

    @Override
    public void updateStatus(String userId, String status) throws UserNotFoundException {
        AppUser user = findUserById(userId);
        user.setStatus(status);
        userRepository.save(user);
    }

    @Override
    public Set<String> getHighStories(String userId) throws UserNotFoundException {
        AppUser user = findUserById(userId);
        return user.getHigh();
    }

    @Override
    public void addHighStory(String userId, String storyId) throws UserNotFoundException {
        AppUser user = findUserById(userId);
        user.getHigh().add(storyId);
        userRepository.save(user);
    }

    @Override
    public void removeHighStory(String userId, String storyId) throws UserNotFoundException {
        AppUser user = findUserById(userId);
        user.getHigh().remove(storyId);
        userRepository.save(user);
    }
}
