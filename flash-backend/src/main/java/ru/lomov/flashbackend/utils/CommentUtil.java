package ru.lomov.flashbackend.utils;

import ru.lomov.flashbackend.entities.AppLike;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.Comment;

import java.util.Objects;

public class CommentUtil {
    public static boolean isLikedByReqUser(AppUser reqUser, Comment comment) {
        Objects.requireNonNull(reqUser, "ReqUser cannot be null");
        Objects.requireNonNull(comment, "Comment cannot be null");

        for (AppLike appLike : comment.getLikes()) {
            if (appLike.getAppUser().getUserId().equals(reqUser.getUserId())) {
                return true;
            }
        }
        return false;
    }
}