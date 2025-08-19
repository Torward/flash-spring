package ru.lomov.flashbackend.utils;

import ru.lomov.flashbackend.dto.LikeDto;
import ru.lomov.flashbackend.dto.PostDto;
import ru.lomov.flashbackend.dto.UserDto;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.AppLike;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LikeDtoMapper {
    public static LikeDto likeToDto(AppLike appLike, AppUser reqUser) {
        Objects.requireNonNull(appLike, "AppLike cannot be null");
        Objects.requireNonNull(reqUser, "ReqUser cannot be null");

        UserDto user = UserDtoMapper.userToDto(appLike.getAppUser());
        PostDto post = PostDtoMapper.postToDto(appLike.getPost(), reqUser);
        LikeDto likeDto = new LikeDto();
        likeDto.setId(appLike.getId());
        likeDto.setPost(post);
        likeDto.setUser(user);

        return likeDto;
    }

    public static List<LikeDto> likeToDtos(List<AppLike> appLikes, AppUser reqUser) {
        Objects.requireNonNull(appLikes, "AppLikes cannot be null");
        Objects.requireNonNull(reqUser, "ReqUser cannot be null");

        List<LikeDto> likeDtos = new ArrayList<>();
        for (AppLike appLike : appLikes) {
            likeDtos.add(likeToDto(appLike, reqUser));
        }
        return likeDtos;
    }
}