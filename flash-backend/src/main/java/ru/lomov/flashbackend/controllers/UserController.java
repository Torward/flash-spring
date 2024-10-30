
package ru.lomov.flashbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flashbackend.dto.UserDto;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.exceptions.UserNotFoundException;
import ru.lomov.flashbackend.services.PostService;
import ru.lomov.flashbackend.services.UserService;
import ru.lomov.flashbackend.utils.UserDtoMapper;
import ru.lomov.flashbackend.utils.UserUtil;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final PostService postService;

    @GetMapping("/profile")
    public UserDto getUserProfile(@RequestHeader("Authorization") String jwt) throws UserNotFoundException {
        AppUser user = userService.findUserProfileByJwt(jwt);
        UserDto userDto = UserDtoMapper.userToDto(user);
        userDto.setReq_user(true);
        return userDto;
    }


    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserNotFoundException {
        AppUser reqUser = userService.findUserProfileByJwt(jwt);

        AppUser user = userService.findUserById(userId);
        UserDto userDto = UserDtoMapper.userToDto(user);
        userDto.setReq_user(UserUtil.isReqUser(reqUser, user));
        userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));
        return userDto;
    }

    @PutMapping
    public UserDto updateUser(@RequestBody AppUser req, @RequestHeader("Authorization") String jwt) throws UserNotFoundException {
        AppUser reqUser = userService.findUserProfileByJwt(jwt);
        AppUser user = userService.updateUser(reqUser.getUserId(), req);
        UserDto userDto = UserDtoMapper.userToDto(user);
        return userDto;
    }
    @PutMapping("/{userId}/follow")
    public UserDto followUser(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserNotFoundException {
        AppUser reqUser = userService.findUserProfileByJwt(jwt);
        AppUser user = userService.followUser(userId, reqUser);
        UserDto userDto = UserDtoMapper.userToDto(user);
        userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));
        return userDto;
    }
    @PutMapping("/{userId}/unfollow")
    public UserDto unfollowUser(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserNotFoundException {
        AppUser reqUser = userService.findUserProfileByJwt(jwt);
        AppUser user = userService.unfollowUser(userId, reqUser);
        UserDto userDto = UserDtoMapper.userToDto(user);
        userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));
        return userDto;
    }
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws UserNotFoundException {
        AppUser reqUser = userService.findUserProfileByJwt(jwt);
        userService.deleteUser(userId, reqUser);
    }



}
