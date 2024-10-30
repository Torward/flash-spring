package ru.lomov.flashbackend.dto;

import lombok.Data;

@Data
public class LikeDto {
    private Long id;
    private UserDto user;
    private PostDto post;
    private CommentDto comment;
    private StatusDto status;
}
