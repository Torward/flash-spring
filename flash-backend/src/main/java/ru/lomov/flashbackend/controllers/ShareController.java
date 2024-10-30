package ru.lomov.flashbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flashbackend.dto.LikeDto;
import ru.lomov.flashbackend.dto.ShareDto;
import ru.lomov.flashbackend.entities.AppLike;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.Share;
import ru.lomov.flashbackend.exceptions.PostNotFoundException;
import ru.lomov.flashbackend.exceptions.UserNotFoundException;
import ru.lomov.flashbackend.services.ShareService;
import ru.lomov.flashbackend.services.UserService;
import ru.lomov.flashbackend.utils.LikeDtoMapper;
import ru.lomov.flashbackend.utils.ShareDtoMapper;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ShareController {
    private final ShareService shareService;
    private final UserService userService;

    @PostMapping("/{postId}/share")
    public ResponseEntity<ShareDto> sharePost(@PathVariable Long postId, @RequestHeader("Authorization") String jwt) throws PostNotFoundException, UserNotFoundException {
        AppUser user = userService.findUserProfileByJwt(jwt);
        Share share = shareService.sharePost(postId, user);
        ShareDto shareDto = ShareDtoMapper.shareToDto(share, user);
        return new ResponseEntity<>(shareDto, HttpStatus.CREATED);
    }

    @PostMapping("/post/share/{postId}")
    public ResponseEntity<List<ShareDto>> getAllShares(@PathVariable Long postId, @RequestHeader("Authorization") String jwt) throws PostNotFoundException, UserNotFoundException {
        AppUser user = userService.findUserProfileByJwt(jwt);
        List<Share> shares = shareService.getAllShares(postId);
        List<ShareDto> shareDtos = ShareDtoMapper.shareToDtos(shares, user);
        return new ResponseEntity<>(shareDtos, HttpStatus.CREATED);
    }

}
