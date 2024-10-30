package ru.lomov.flashbackend.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostDto {
    private Long id;
    private String content;
    private String image;
    private String video;
    private String audio;
    private UserDto user;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int totalLikes;
    private int totalReplies;
    private int totalReposts;
    private int totalShares;
    private boolean isLiked;
    private boolean isReposted;
    private List<Long> repostUsersId;
    private List<PostDto> replyPost;
}