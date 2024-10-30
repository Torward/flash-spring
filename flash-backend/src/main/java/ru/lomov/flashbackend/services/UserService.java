package ru.lomov.flashbackend.services;

import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.exceptions.UserNotFoundException;

import java.util.List;

public interface UserService {
    AppUser findUserById(Long userId) throws UserNotFoundException;
    AppUser findUserByEmail(String email) throws UserNotFoundException;
    AppUser findUserProfileByJwt(String jwt) throws UserNotFoundException;
    AppUser updateUser(Long userId, AppUser user) throws UserNotFoundException;
    AppUser followUser(Long userId, AppUser user) throws UserNotFoundException;

    void updatePassword(AppUser user, String newPassword);
    void sendPasswordResetEmail(AppUser user);
    List<AppUser> searchUser(String query);

    void deleteUser(Long userId, AppUser reqUser);

    AppUser unfollowUser(Long userId, AppUser reqUser);

    void save(AppUser user);
}