
package ru.lomov.flashbackend.utils;

import ru.lomov.flashbackend.dto.PostDto;
import ru.lomov.flashbackend.dto.UserDto;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.Post;

import java.util.ArrayList;
import java.util.List;

public class PostDtoMapper {
    public static PostDto postToDto(Post post, AppUser reqUser){
        UserDto userDto = UserDtoMapper.userToDto(post.getAppUser());
        boolean isLiked = PostUtil.isLikedByReqUser(reqUser, post);
        boolean isReposted = PostUtil.isRepostedByReqUser(reqUser, post);
        boolean isBookmarked = PostUtil.isBookmarkedByReqUser(reqUser, post);
        List<Long> repostUserId = new ArrayList<>();
        for (AppUser user: post.getRepostAppUser()) {
            repostUserId.add(user.getUserId());
        }
        
        PostDto postDto = PostDto.builder()
                .id(post.getPostId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .image(post.getImage())
                .video(post.getVideo())
                .audio(post.getAudio())
                .totalLikes(post.getLikes().size())
                .totalReplies(post.getReplyPosts().size())
                .totalReposts(post.getRepostAppUser().size())
                .totalShares(post.getShares().size())
                .bookmarkCount((long) post.getAppUser().getSavedPosts().size())
                .user(userDto)
                .isLiked(isLiked)
                .isReposted(isReposted)
                .isBookmarked(isBookmarked)
                .repostUsersId(repostUserId)
                .replyPost(postToDto(post.getReplyPosts(), reqUser))
                .video(post.getVideo())
                .build();
        return postDto;
    }

    public static List<PostDto> postToDto (List<Post> posts, AppUser reqUser){
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post: posts) {
            PostDto postDto = toReplyPostDto(post, reqUser);
            postDtos.add(postDto);
        }
        return postDtos;
    }

    private static PostDto toReplyPostDto(Post post, AppUser reqUser) {
        UserDto userDto = UserDtoMapper.userToDto(post.getAppUser());
        boolean isLiked = PostUtil.isLikedByReqUser(reqUser, post);
        boolean isReposted = PostUtil.isRepostedByReqUser(reqUser, post);
        boolean isBookmarked = PostUtil.isBookmarkedByReqUser(reqUser, post);
        List<Long> repostUserId = new ArrayList<>();
        for (AppUser user: post.getRepostAppUser()) {
            repostUserId.add(user.getUserId());
        }
        PostDto postDto = PostDto.builder()
                .id(post.getPostId())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .image(post.getImage())
                .video(post.getVideo())
                .audio(post.getAudio())
                .totalLikes(post.getLikes().size())
                .totalShares(post.getShares().size())
                .totalReplies(post.getReplyPosts().size())
                .totalReposts(post.getRepostAppUser().size())
                .bookmarkCount((long) post.getAppUser().getSavedPosts().size())
                .user(userDto)
                .isLiked(isLiked)
                .isReposted(isReposted)
                .isBookmarked(isBookmarked)
                .repostUsersId(repostUserId)
                .video(post.getVideo())
                .build();
        return postDto;
    }
}
