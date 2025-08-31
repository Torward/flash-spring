package ru.lomov.flashbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flashbackend.dto.UserDto;
import ru.lomov.flashbackend.dto.UserProfileDto;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.exceptions.UserNotFoundException;
import ru.lomov.flashbackend.services.UserService;
import ru.lomov.flashbackend.utils.UserDtoMapper;
import ru.lomov.flashbackend.utils.UserUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @GetMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId, Authentication authentication) throws UserNotFoundException {
        String email = authentication.getName();
        AppUser reqUser = userService.findUserByEmail(email);
        AppUser user = userService.findUserById(userId);
        UserDto userDto = UserDtoMapper.userToDto(user);
        userDto.setReq_user(UserUtil.isReqUser(reqUser, user));
        userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));
        logger.info("User retrieved by ID: {} by user: {}", userId, email);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/profile/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable String userId, Authentication authentication) throws UserNotFoundException {
        String email = authentication.getName();
        AppUser reqUser = userService.findUserByEmail(email);
        AppUser user = userService.findUserById(userId);
        
        UserProfileDto profileDto = UserDtoMapper.userToProfileDto(user);
        profileDto.setReqUser(UserUtil.isReqUser(reqUser, user));
        profileDto.setFollowing(UserUtil.isFollowedByReqUser(reqUser, user));
        profileDto.setFollowedBy(UserUtil.isFollowingReqUser(reqUser, user));
        
        logger.info("User profile retrieved: {} by user: {}", user.getEmail(), email);
        return ResponseEntity.ok(profileDto);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody AppUser user) {
        AppUser createdUser = userService.createUser(user);
        UserDto userDto = UserDtoMapper.userToDto(createdUser);
        logger.info("User created: {}", createdUser.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PutMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> updateUser(@PathVariable String userId, @RequestBody AppUser req, Authentication authentication) throws UserNotFoundException {
        String email = authentication.getName();
        AppUser reqUser = userService.findUserByEmail(email);
        
        if (!reqUser.getUserId().equals(userId)) {
            throw new SecurityException("You can only update your own profile");
        }
        
        AppUser user = userService.updateUser(userId, req);
        UserDto userDto = UserDtoMapper.userToDto(user);
        logger.info("User updated: {} by user: {}", user.getEmail(), email);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId, Authentication authentication) throws UserNotFoundException {
        String email = authentication.getName();
        AppUser reqUser = userService.findUserByEmail(email);
        
        if (!reqUser.getUserId().equals(userId)) {
            throw new SecurityException("You can only delete your own profile");
        }
        
        userService.deleteUser(userId, reqUser);
        logger.info("User deleted: {} by user: {}", userId, email);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> getUserProfile(Authentication authentication) throws UserNotFoundException {
        String email = authentication.getName();
        AppUser user = userService.findUserByEmail(email);
        UserDto userDto = UserDtoMapper.userToDto(user);
        userDto.setReq_user(true);
        logger.info("User profile retrieved: {}", email);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{userId}/follow")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> followUser(@PathVariable String userId, Authentication authentication) throws UserNotFoundException {
        String email = authentication.getName();
        AppUser reqUser = userService.findUserByEmail(email);
        userService.followUser(reqUser.getUserId(), userId);
        AppUser user = userService.findUserById(userId);
        UserDto userDto = UserDtoMapper.userToDto(user);
        userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));
        logger.info("User followed: {} by user: {}", user.getEmail(), email);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{userId}/unfollow")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> unfollowUser(@PathVariable String userId, Authentication authentication) throws UserNotFoundException {
        String email = authentication.getName();
        AppUser reqUser = userService.findUserByEmail(email);
        userService.unfollowUser(reqUser.getUserId(), userId);
        AppUser user = userService.findUserById(userId);
        UserDto userDto = UserDtoMapper.userToDto(user);
        userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));
        logger.info("User unfollowed: {} by user: {}", user.getEmail(), email);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<UserDto>> searchUsers(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Authentication authentication) throws UserNotFoundException {
        
        String email = authentication.getName();
        AppUser reqUser = userService.findUserByEmail(email);
        Pageable pageable = PageRequest.of(page, size);
        
        Page<AppUser> users = userService.searchUsers(query, pageable);
        Page<UserDto> userDtos = users.map(user -> {
            UserDto dto = UserDtoMapper.userToDto(user);
            dto.setReq_user(UserUtil.isReqUser(reqUser, user));
            dto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));
            return dto;
        });
        
        logger.info("Users searched with query: {} by user: {}", query, email);
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/{userId}/followers")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<UserDto>> getUserFollowers(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Authentication authentication) throws UserNotFoundException {
        
        String email = authentication.getName();
        AppUser reqUser = userService.findUserByEmail(email);
        Pageable pageable = PageRequest.of(page, size);
        
        Page<AppUser> followers = userService.getUserFollowers(userId, pageable);
        Page<UserDto> followerDtos = followers.map(user -> {
            UserDto dto = UserDtoMapper.userToDto(user);
            dto.setReq_user(UserUtil.isReqUser(reqUser, user));
            dto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));
            return dto;
        });
        
        logger.info("Followers retrieved for user: {} by user: {}", userId, email);
        return ResponseEntity.ok(followerDtos);
    }

    @GetMapping("/{userId}/following")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<UserDto>> getUserFollowing(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Authentication authentication) throws UserNotFoundException {
        
        String email = authentication.getName();
        AppUser reqUser = userService.findUserByEmail(email);
        Pageable pageable = PageRequest.of(page, size);
        
        Page<AppUser> following = userService.getUserFollowing(userId, pageable);
        Page<UserDto> followingDtos = following.map(user -> {
            UserDto dto = UserDtoMapper.userToDto(user);
            dto.setReq_user(UserUtil.isReqUser(reqUser, user));
            dto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));
            return dto;
        });
        
        logger.info("Following retrieved for user: {} by user: {}", userId, email);
        return ResponseEntity.ok(followingDtos);
    }

    @PutMapping("/{userId}/interests")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> updateUserInterests(
            @PathVariable String userId,
            @RequestBody Set<String> interests,
            Authentication authentication) throws UserNotFoundException {
        
        String email = authentication.getName();
        AppUser reqUser = userService.findUserByEmail(email);
        
        if (!reqUser.getUserId().equals(userId)) {
            throw new SecurityException("You can only update your own interests");
        }
        
        AppUser user = userService.updateUserInterests(userId, interests);
        UserDto userDto = UserDtoMapper.userToDto(user);
        logger.info("User interests updated: {} by user: {}", user.getEmail(), email);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{userId}/skills")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> updateUserSkills(
            @PathVariable String userId,
            @RequestBody Set<String> skills,
            Authentication authentication) throws UserNotFoundException {
        
        String email = authentication.getName();
        AppUser reqUser = userService.findUserByEmail(email);
        
        if (!reqUser.getUserId().equals(userId)) {
            throw new SecurityException("You can only update your own skills");
        }
        
        AppUser user = userService.updateUserSkills(userId, skills);
        UserDto userDto = UserDtoMapper.userToDto(user);
        logger.info("User skills updated: {} by user: {}", user.getEmail(), email);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{userId}/settings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDto> updateUserSettings(
            @PathVariable String userId,
            @RequestBody Map<String, Object> settings,
            Authentication authentication) throws UserNotFoundException {
        
        String email = authentication.getName();
        AppUser reqUser = userService.findUserByEmail(email);
        
        if (!reqUser.getUserId().equals(userId)) {
            throw new SecurityException("You can only update your own settings");
        }
        
        AppUser user = userService.updateUserSettings(userId, settings);
        UserDto userDto = UserDtoMapper.userToDto(user);
        logger.info("User settings updated: {} by user: {}", user.getEmail(), email);
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/recommended")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserDto>> getRecommendedUsers(
            @RequestParam(defaultValue = "10") int limit,
            Authentication authentication) throws UserNotFoundException {
        
        String email = authentication.getName();
        AppUser reqUser = userService.findUserByEmail(email);
        
        List<AppUser> recommendedUsers = userService.getRecommendedUsers(reqUser.getUserId(), limit);
        List<UserDto> userDtos = recommendedUsers.stream()
                .map(user -> {
                    UserDto dto = UserDtoMapper.userToDto(user);
                    dto.setReq_user(UserUtil.isReqUser(reqUser, user));
                    dto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));
                    return dto;
                })
                .toList();
        
        logger.info("Recommended users retrieved for user: {}", email);
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/stats/{userId")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Map<String, Object>>getUserStats(
            @PathVariable String userId,
            Authentication authentication) throws UserNotFoundException {
        
        String email = authentication.getName();
        AppUser reqUser = userService.findUserByEmail(email);
        
        if (!reqUser.getUserId().equals(userId)) {
            throw new SecurityException("You can only view your own stats");
        }
        
        Map<String, Object> stats = userService.getUserStats(userId);
        logger.info("User stats retrieved: {} by user: {}", userId, email);
        return ResponseEntity.ok(stats);
    }

    // Firebase-compatible endpoints

    @GetMapping("/{userId}/blocked")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Set<String>> getBlockedUsers(@PathVariable String userId, Authentication authentication) throws UserNotFoundException {
        String email = authentication.getName();
        AppUser reqUser = userService.findUserByEmail(email);
        
        if (!reqUser.getUserId().equals(userId)) {
            throw new SecurityException("You can only view your own blocked users");
        }
        
        Set<String> blockedUsers = userService.getBlockedUsers(userId);
        logger.info("Blocked users retrieved for user: {} by user: {}", userId, email);
        return ResponseEntity.ok(blockedUsers);
    }

    @PostMapping("/{userId}/block/{blockedUserId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> blockUser(@PathVariable String userId, @PathVariable String blockedUserId, Authentication authentication) throws UserNotFoundException {
        String email = authentication.getName();
        AppUser reqUser = userService.findUserByEmail(email);
        
        if (!reqUser.getUserId().equals(userId)) {
            throw new SecurityException("You can only block users from your own account");
        }
        
        userService.blockUser(userId, blockedUserId);
        logger.info("User {} blocked by user: {}", blockedUserId, email);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/block/{blockedUserId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> unblockUser(@PathVariable String userId, @PathVariable String blockedUserId, Authentication authentication) throws UserNotFoundException {
        String email = authentication.getName();
        AppUser reqUser = userService.findUserByEmail(email);
        
        if (!reqUser.getUserId().equals(userId)) {
            throw new SecurityException("You can only unblock users from your own account");
        }
        
        userService.unblockUser(userId, blockedUserId);
        logger.info("User {} unblocked by user: {}", blockedUserId, email);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/notifications")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Set<String>> getNotifications(@PathVariable String userId, Authentication authentication) throws UserNotFoundException {
        String email = authentication.getName();
        AppUser reqUser = userService.findUserByEmail(email);
        
        if (!reqUser.getUserId().equals(userId)) {
            throw new SecurityException("You can only view your own notifications");
        }
        
        Set<String> notifications = userService.getNotifications(userId);
        logger.info("Notifications retrieved for user: {} by user: {}", userId, email);
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/{userId}/notifications/{notificationId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> addNotification(@PathVariable String userId, @PathVariable String notificationId, Authentication authentication) throws UserNotFoundException {
        String email = authentication.getName();
        AppUser reqUser = userService.findUserByEmail(email);
        
        if (!reqUser.getUserId().equals(userId)) {
            throw new SecurityException("You can only add notifications to your own account");
        }
        
        userService.addNotification(userId, notificationId);
        logger.info("Notification {} added for user: {} by user: {}", notificationId, userId, email);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/notifications/{notificationId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> removeNotification(@PathVariable String userId, @PathVariable String notificationId, Authentication authentication) throws UserNotFoundException {
        String email = authentication.getName();
        AppUser reqUser = userService.findUserByEmail(email);
        
        if (!reqUser.getUserId().equals(userId)) {
            throw new SecurityException("You can only remove notifications from your own account");
        }
        
        userService.removeNotification(userId, notificationId);
        logger.info("Notification {} removed for user: {} by user: {}", notificationId, userId, email);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{userId}/status")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> updateStatus(@PathVariable String userId, @RequestParam String status, Authentication authentication) throws UserNotFoundException {
        String email = authentication.getName();
