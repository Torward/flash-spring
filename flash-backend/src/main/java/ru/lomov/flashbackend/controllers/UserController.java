
package ru.lomov.flashbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flashbackend.dto.UserDto;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.exceptions.UserNotFoundException;
import ru.lomov.flashbackend.services.UserService;
import ru.lomov.flashbackend.utils.UserDtoMapper;
import ru.lomov.flashbackend.utils.UserUtil;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @GetMapping("/profile")
    public UserDto getUserProfile(@RequestHeader("Authorization") String jwt) throws UserNotFoundException {
        AppUser user = userService.findUserProfileByJwt(jwt);
        UserDto userDto = UserDtoMapper.userToDto(user);
        userDto.setReq_user(true);
        logger.info("User profile retrieved: {}", user.getEmail());
        return userDto;
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserNotFoundException {
        AppUser reqUser = userService.findUserProfileByJwt(jwt);
        AppUser user = userService.findUserById(userId);
        UserDto userDto = UserDtoMapper.userToDto(user);
        userDto.setReq_user(UserUtil.isReqUser(reqUser, user));
        userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));
        logger.info("User retrieved by ID: {}", userId);
        return userDto;
    }

    @PutMapping
    public UserDto updateUser(@RequestBody AppUser req, @RequestHeader("Authorization") String jwt) throws UserNotFoundException {
        AppUser reqUser = userService.findUserProfileByJwt(jwt);
        AppUser user = userService.updateUser(reqUser.getUserId(), req);
        UserDto userDto = UserDtoMapper.userToDto(user);
        logger.info("User updated: {}", user.getEmail());
        return userDto;
    }

    @PutMapping("/{userId}/follow")
    public UserDto followUser(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserNotFoundException {
        AppUser reqUser = userService.findUserProfileByJwt(jwt);
        AppUser user = userService.followUser(userId, reqUser);
        UserDto userDto = UserDtoMapper.userToDto(user);
        userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));
        logger.info("User followed: {}", user.getEmail());
        return userDto;
    }

    @PutMapping("/{userId}/unfollow")
    public UserDto unfollowUser(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserNotFoundException {
        AppUser reqUser = userService.findUserProfileByJwt(jwt);
        AppUser user = userService.unfollowUser(userId, reqUser);
        UserDto userDto = UserDtoMapper.userToDto(user);
        userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));
        logger.info("User unfollowed: {}", user.getEmail());
        return userDto;
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserNotFoundException {
        AppUser reqUser = userService.findUserProfileByJwt(jwt);
        userService.deleteUser(userId, reqUser);
        logger.info("User deleted: {}", userId);
    }
}