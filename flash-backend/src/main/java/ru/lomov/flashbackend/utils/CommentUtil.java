package ru.lomov.flashbackend.utils;

import ru.lomov.flashbackend.entities.AppLike;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.Comment;
import ru.lomov.flashbackend.entities.Post;

public class CommentUtil {
    public static boolean isLikedByReqUser(AppUser reqUser, Comment comment) {
        for (AppLike appLike : comment.getLikes()) {
            if (appLike.getAppUser().getUserId().equals(reqUser.getUserId())){
                return true;
            }
        }
        return false;
    }
}
