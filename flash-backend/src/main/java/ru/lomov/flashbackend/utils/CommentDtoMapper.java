package ru.lomov.flashbackend.utils;

import ru.lomov.flashbackend.dto.CommentDto;
import ru.lomov.flashbackend.dto.UserDto;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentDtoMapper {
    public static CommentDto commentToDto(Comment comment, AppUser reqUser) {
        UserDto userDto = UserDtoMapper.userToDto(comment.getUser());
        boolean isLiked = CommentUtil.isLikedByReqUser(reqUser, comment);
        CommentDto commentDto = CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .media(comment.getMedia())
                .totalLikes(comment.getLikes().size())
                .totalReplies(comment.getReplyComments().size())
                .user(userDto)
                .isLiked(isLiked)
                .replyComments(commentToDto(comment.getReplyComments(), reqUser))
                .build();
        return commentDto;
    }

    public static List<CommentDto> commentToDto(List<Comment> comments, AppUser reqUser) {
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDto commentDto = toReplyCommentDto(comment, reqUser);
            commentDtos.add(commentDto);
        }
        return commentDtos;
    }

    private static CommentDto toReplyCommentDto(Comment comment, AppUser reqUser) {
        UserDto userDto = UserDtoMapper.userToDto(comment.getUser());
        boolean isLiked = CommentUtil.isLikedByReqUser(reqUser, comment);
        CommentDto commentDto = CommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .media(comment.getMedia())
                .totalLikes(comment.getLikes().size())
                .totalReplies(comment.getReplyComments().size())
                .user(userDto)
                .isLiked(isLiked)
                .build();
        return commentDto;
    }
}
