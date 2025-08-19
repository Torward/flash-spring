package ru.lomov.flashbackend.utils;

import ru.lomov.flashbackend.dto.PostDto;
import ru.lomov.flashbackend.dto.ShareDto;
import ru.lomov.flashbackend.dto.UserDto;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.Share;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ShareDtoMapper {
    public static ShareDto shareToDto(Share share, AppUser reqUser) {
        Objects.requireNonNull(share, "Share cannot be null");
        Objects.requireNonNull(reqUser, "ReqUser cannot be null");

        UserDto user = UserDtoMapper.userToDto(share.getAppUser());
        PostDto post = PostDtoMapper.postToDto(share.getPost(), reqUser);
        ShareDto shareDto = new ShareDto();
        shareDto.setId(share.getId());
        shareDto.setPost(post);
        shareDto.setUser(user);
        return shareDto;
    }

    public static List<ShareDto> shareToDtos(List<Share> shares, AppUser reqUser) {
        Objects.requireNonNull(shares, "Shares cannot be null");
        Objects.requireNonNull(reqUser, "ReqUser cannot be null");

        List<ShareDto> shareDtos = new ArrayList<>();
        for (Share share : shares) {
            shareDtos.add(shareToDto(share, reqUser));
        }
        return shareDtos;
    }
}