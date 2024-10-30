package ru.lomov.flashbackend.utils;

import ru.lomov.flashbackend.entities.AppLike;
import ru.lomov.flashbackend.entities.AppUser;

import ru.lomov.flashbackend.entities.Post;

public class PostUtil {
    public static boolean isLikedByReqUser(AppUser reqUser, Post post) {
        for (AppLike appLike : post.getLikes()) {
            if (appLike.getAppUser().getUserId().equals(reqUser.getUserId())){
                return true;
            }
        }
        return false;
    }

    public static boolean isCommentedByReqUser(AppUser reqUser, Post post){
        return isRepostedByReqUser(reqUser, post);
    }

    public static boolean isRepostedByReqUser(AppUser reqUser, Post post){
        for (AppUser user: post.getRepostAppUser()) {
            if (user.getUserId().equals(reqUser.getUserId())){
                return true;
            }
        }
        return false;
    }
}