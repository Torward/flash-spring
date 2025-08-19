package ru.lomov.flashbackend.dto;

import lombok.Builder;
import lombok.Data;
import ru.lomov.flashbackend.dto.UserDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PostDto {
    private Long id;
    private String content;
    private String image;
    private String video;
    private String audio;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private UserDto user;
    private Long totalLikes;
    private Long totalReplies;
    private Long totalReposts;
    private Long totalShares;
    private boolean isLiked;
    private boolean isReposted;
    private boolean isBookmarked;
    private Long bookmarkCount;
    private List<Long> repostUsersId;
    private List<PostDto> replyPost;
}
