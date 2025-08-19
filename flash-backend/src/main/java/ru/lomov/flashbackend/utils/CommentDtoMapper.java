package ru.lomov.flashbackend.utils;

import ru.lomov.flashbackend.dto.CommentDto;
import ru.lomov.flashbackend.dto.UserDto;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommentDtoMapper {
    public static CommentDto commentToDto(Comment comment, AppUser reqUser) {
        Objects.requireNonNull(comment, "Comment cannot be null");
        Objects.requireNonNull(reqUser, "ReqUser cannot be null");

        return toReplyCommentDto(comment, reqUser);
    }

    public static List<CommentDto> commentToDto(List<Comment> comments, AppUser reqUser) {
        Objects.requireNonNull(comments, "Comments cannot be null");
        Objects.requireNonNull(reqUser, "ReqUser cannot be null");

        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            commentDtos.add(toReplyCommentDto(comment, reqUser));
        }
        return commentDtos;
    }

    private static CommentDto toReplyCommentDto(Comment comment, AppUser reqUser) {
        UserDto userDto = UserDtoMapper.userToDto(comment.getUser());
        boolean isLiked = CommentUtil.isLikedByReqUser(reqUser, comment);
        return CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .media(comment.getMedia())
                .totalLikes(comment.getLikes().size())
                .totalReplies(comment.getReplyComments().size())
                .user(userDto)
                .isLiked(isLiked)
                .build();
    }
}