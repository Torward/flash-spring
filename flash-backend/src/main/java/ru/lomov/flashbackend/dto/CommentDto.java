package ru.lomov.flashbackend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CommentDto {
    private Long id;
    private UserDto user;
    private LocalDateTime createdAt;
    private StatusDto status;
    private PostDto post;
    private String content;
    private String media;
    private int totalReplies;
    private int totalLikes;
    private boolean isLiked;
    private List<CommentDto> replyComments;
}
