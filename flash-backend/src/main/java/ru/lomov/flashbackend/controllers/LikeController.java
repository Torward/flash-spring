package ru.lomov.flashbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flashbackend.dto.LikeDto;
import ru.lomov.flashbackend.dto.PostDto;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.AppLike;
import ru.lomov.flashbackend.exceptions.PostNotFoundException;
import ru.lomov.flashbackend.exceptions.UserNotFoundException;
import ru.lomov.flashbackend.services.LikeService;
import ru.lomov.flashbackend.services.UserService;
import ru.lomov.flashbackend.utils.LikeDtoMapper;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {
    private final LikeService likeService;
    private final UserService userService;

    @PostMapping("/{postId}/like")
    public ResponseEntity<LikeDto> likePost(@PathVariable Long postId, @RequestHeader("Authorization") String jwt) throws PostNotFoundException, UserNotFoundException {
        AppUser user = userService.findUserProfileByJwt(jwt);
        AppLike appLike = likeService.likePost(postId, user);
        LikeDto likeDto = LikeDtoMapper.likeToDto(appLike, user);
        return new ResponseEntity<>(likeDto, HttpStatus.CREATED);
    }

    @PostMapping("/post/{postId}")
    public ResponseEntity<List<LikeDto>> getAllLikes(@PathVariable Long postId, @RequestHeader("Authorization") String jwt) throws PostNotFoundException, UserNotFoundException {
        AppUser user = userService.findUserProfileByJwt(jwt);
        List<AppLike> appLikes = likeService.getAllLikes(postId);
        List<LikeDto> likeDtos = LikeDtoMapper.likeToDtos(appLikes, user);
        return new ResponseEntity<>(likeDtos, HttpStatus.CREATED);
    }

}
