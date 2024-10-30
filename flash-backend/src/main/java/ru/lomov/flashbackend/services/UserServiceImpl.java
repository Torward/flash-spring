package ru.lomov.flashbackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flashbackend.config.JwtProvider;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.exceptions.UserNotFoundException;
import ru.lomov.flashbackend.repositories.UserRepository;


import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Override
    public AppUser findUserById(Long userId) throws UserNotFoundException {
        AppUser user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("Пользователь c номером " + userId + " не найден!"));
        return user;
    }

    @Override
    public AppUser findUserByEmail(String email) throws UserNotFoundException {
        AppUser user = userRepository.findAppUserByEmail(email).orElseThrow(
                () -> new UserNotFoundException("Пользователь с адресом электронной почты " + email + " не найден!"));
        return user;
    }

    @Override
    public AppUser findUserProfileByJwt(String jwt) throws UserNotFoundException {
        String email = jwtProvider.getEmailFromToken(jwt);
        AppUser user = userRepository.findAppUserByEmail(email).orElseThrow(
                () -> new UserNotFoundException("Пользователь с адресом электронной почты " + email + " не найден!"));
        if (user == null) {
            throw new UserNotFoundException("Пользователь не найден!");
        }
        return user;
    }

    @Override
    public AppUser updateUser(Long userId, AppUser reqUser) throws UserNotFoundException {
        AppUser user = findUserById(userId);
        if (reqUser.getFullName() != null) {
            user.setFullName(reqUser.getFullName());
        }
        if (reqUser.getImage() != null) {
            user.setImage(reqUser.getImage());
        }
        if (reqUser.getBackgroundImage() != null) {
            user.setBackgroundImage(reqUser.getBackgroundImage());
        }
        if (reqUser.getLocation() != null) {
            user.setLocation(reqUser.getLocation());
        }
        if (reqUser.getBirthDate() != null) {
            user.setBirthDate(reqUser.getBirthDate());
        }
        if (reqUser.getBio() != null) {
            user.setBio(reqUser.getBio());
        }
        if (reqUser.getWebsite() != null) {
            user.setWebsite(reqUser.getWebsite());
        }
        if (reqUser.getGender() != null) {
            user.setGender(reqUser.getGender());
        }

          return userRepository.save(user);
    }

    @Override
    public AppUser followUser(Long userId, AppUser user) throws UserNotFoundException {
        AppUser followToUser = findUserById(userId);
        if (user.getFollowing().contains(followToUser) && followToUser.getFollowers().contains(user)) {
            user.getFollowing().remove(followToUser);
            followToUser.getFollowers().remove(user);
        } else {
            user.getFollowing().add(followToUser);
            followToUser.getFollowers().add(user);
        }
        userRepository.save(followToUser);
        userRepository.save(user);
        return followToUser;
    }



    @Override
    public void updatePassword(AppUser user, String newPassword) {

    }

    @Override
    public void sendPasswordResetEmail(AppUser user) {

    }

    @Override
    public List<AppUser> searchUser(String query) {
        return userRepository.searchUser(query);
    }

    @Override
    public void deleteUser(Long userId, AppUser reqUser) {
        AppUser user = findUserById(userId);
        if(reqUser.equals(user)){
            userRepository.delete(user);
        }
    }

    @Override
    public AppUser unfollowUser(Long userId, AppUser reqUser) {
        AppUser followToUser = findUserById(userId);
        if (reqUser.getFollowing().contains(followToUser) && followToUser.getFollowers().contains(reqUser)) {
            reqUser.getFollowing().remove(followToUser);
            followToUser.getFollowers().remove(reqUser);
        }
        userRepository.save(followToUser);
        userRepository.save(reqUser);
        return followToUser;
    }

    @Override
    public void save(AppUser user) {
        userRepository.save(user);
    }
}
