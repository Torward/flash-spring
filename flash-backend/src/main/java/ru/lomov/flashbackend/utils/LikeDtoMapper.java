
package ru.lomov.flashbackend.utils;

import ru.lomov.flashbackend.dto.LikeDto;
import ru.lomov.flashbackend.dto.PostDto;
import ru.lomov.flashbackend.dto.UserDto;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.AppLike;

import java.util.ArrayList;
import java.util.List;

public class LikeDtoMapper {
    public static LikeDto likeToDto(AppLike appLike, AppUser reqUser) {
        UserDto user = UserDtoMapper.userToDto(appLike.getAppUser());
        UserDto reqUserDto = UserDtoMapper.userToDto(reqUser);
        PostDto post = PostDtoMapper.postToDto(appLike.getPost(), reqUser);
        LikeDto likeDto = new LikeDto();
        likeDto.setId(appLike.getId());
        likeDto.setPost(post);
        likeDto.setUser(user);

        return likeDto;
    }

    public static List<LikeDto> likeToDtos(List<AppLike> appLikes, AppUser reqUser) {
        List<LikeDto> likeDtos = new ArrayList<>();
        for (AppLike appLike : appLikes) {
            UserDto user = UserDtoMapper.userToDto(appLike.getAppUser());
            PostDto post = PostDtoMapper.postToDto(appLike.getPost(), reqUser);
            LikeDto likeDto = new LikeDto();
            likeDto.setId(appLike.getId());
            likeDto.setPost(post);
            likeDto.setUser(user);
            likeDtos.add(likeDto);
        }
        return likeDtos;
    }
}
