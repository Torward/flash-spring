package ru.lomov.flashbackend.utils;

import ru.lomov.flashbackend.dto.LikeDto;
import ru.lomov.flashbackend.dto.PostDto;
import ru.lomov.flashbackend.dto.ShareDto;
import ru.lomov.flashbackend.dto.UserDto;
import ru.lomov.flashbackend.entities.AppLike;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.Share;

import java.util.ArrayList;
import java.util.List;

public class ShareDtoMapper {
    public static ShareDto shareToDto(Share share, AppUser reqUser) {
        UserDto user = UserDtoMapper.userToDto(share.getAppUser());
        UserDto reqUserDto = UserDtoMapper.userToDto(reqUser);
        PostDto post = PostDtoMapper.postToDto(share.getPost(), reqUser);
        ShareDto shareDto = new ShareDto();
        shareDto.setId(share.getId());
        shareDto.setPost(post);
        shareDto.setUser(user);
        return shareDto;
    }

    public static List<ShareDto> shareToDtos(List<Share> shares, AppUser reqUser) {
        List<ShareDto> shareDtos = new ArrayList<>();
        for (Share share : shares) {
            UserDto user = UserDtoMapper.userToDto(share.getAppUser());
            PostDto post = PostDtoMapper.postToDto(share.getPost(), reqUser);
            ShareDto shareDto = new ShareDto();
            shareDto.setId(share.getId());
            shareDto.setPost(post);
            shareDto.setUser(user);
            shareDtos.add(shareDto);
        }
        return shareDtos;
    }
}
