package ru.lomov.flashbackend.utils;

import ru.lomov.flashbackend.dto.UserDto;
import ru.lomov.flashbackend.entities.AppUser;

import java.util.ArrayList;
import java.util.List;

public class UserDtoMapper {
    public static UserDto userToDto(AppUser user) {
        UserDto userDto = UserDto.builder()
                .id(user.getUserId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .image(user.getImage())
                .backgroundImage(user.getBackgroundImage())
                .bio(user.getBio())
                .birthDate(user.getBirthDate())
                .followers(userToDto(user.getFollowers()))
                .following(userToDto(user.getFollowing()))
                .login_via_google(user.isLogin_with_google())
                .login_via_vk(user.isLogin_with_vk())
                .location(user.getLocation())
                .coverPictureUrl(user.getCoverPictureUrl())
                .firstName(user.getFirstName())
                .gender(user.getGender())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .profilePictureUrl(user.getProfilePictureUrl())
//                .username(user.getUsername())
                .website(user.getWebsite())
                .websiteCompanyOk(user.getWebsiteCompanyOk())
                .websiteCompanyVk(user.getWebsiteCompanyVk())
                .websiteCompanyTelegram(user.getWebsiteCompanyTelegram())
                .websiteCompanyYoutube(user.getWebsiteCompanyYoutube())
                .websiteCompanyVk(user.getWebsiteCompanyVk())
                .build();
//        userDto.setVerified(false);
        return userDto;
    }

    public static List<UserDto> userToDto(List<AppUser> followers) {
        List<UserDto> userDtos = new ArrayList<>();
        for (AppUser user : followers) {
            UserDto userDto = UserDto.builder()
                    .id(user.getUserId())
                    .email(user.getEmail())
                    .fullName(user.getFullName())
                    .image(user.getImage())
                    .backgroundImage(user.getBackgroundImage())
                    .bio(user.getBio())
                    .birthDate(user.getBirthDate())
                    .followers(userToDto(user.getFollowers()))
                    .following(userToDto(user.getFollowing()))
                    .login_via_google(user.isLogin_with_google())
                    .login_via_vk(user.isLogin_with_vk())
                    .location(user.getLocation())
                    .coverPictureUrl(user.getCoverPictureUrl())
                    .firstName(user.getFirstName())
                    .gender(user.getGender())
                    .lastName(user.getLastName())
                    .phoneNumber(user.getPhoneNumber())
                    .profilePictureUrl(user.getProfilePictureUrl())
//                    .username(user.getUsername())
                    .website(user.getWebsite())
                    .websiteCompanyOk(user.getWebsiteCompanyOk())
                    .websiteCompanyVk(user.getWebsiteCompanyVk())
                    .websiteCompanyTelegram(user.getWebsiteCompanyTelegram())
                    .websiteCompanyYoutube(user.getWebsiteCompanyYoutube())
                    .websiteCompanyVk(user.getWebsiteCompanyVk())
                    .build();
            userDtos.add(userDto);
        }
        return userDtos;
    }
}
