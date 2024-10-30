package ru.lomov.flashbackend.dto;

import lombok.Data;

@Data
public class ShareDto {
    private Long id;
    private UserDto user;
    private PostDto post;
    private StatusDto status;
}
