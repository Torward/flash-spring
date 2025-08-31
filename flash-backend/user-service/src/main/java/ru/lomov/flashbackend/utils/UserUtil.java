package ru.lomov.flashbackend.utils;

import ru.lomov.flashbackend.entities.AppUser;

public class UserUtil {

    public static boolean isReqUser(AppUser reqUser, AppUser user) {
        return reqUser != null && user != null && reqUser.getId().equals(user.getId());
    }

    public static boolean isFollowedByReqUser(AppUser reqUser, AppUser user) {
        if (reqUser == null || user == null) {
            return false;
        }
        // Use direct field access since Lombok should generate getFollowing()
        return reqUser.getFollowing() != null && reqUser.getFollowing().stream()
                .anyMatch(following -> following.equals(user.getId()));
    }

    public static boolean isFollowingReqUser(AppUser reqUser, AppUser user) {
        if (reqUser == null || user == null) {
            return false;
        }
        // Use direct field access since Lombok should generate getFollowing()
        return user.getFollowing() != null && user.getFollowing().stream()
                .anyMatch(following -> following.equals(reqUser.getId()));
    }
}
