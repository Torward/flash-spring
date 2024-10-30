package ru.lomov.flashbackend.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String password;
    private String location;
    private String statement;
    private String website;
    private String birthDate;
    private String phoneNumber;
    private String profilePictureUrl;
    private String coverPictureUrl;
    private String image;
    private String backgroundImage;
    private String bio;
    private String gender;
    private String language;
    private String city;
    private String country;
    private String timezone;
    private String address;
    private String postalCode;
    private String interests;
    private String skills;
    private String education;
    private String occupation;
    private String company;
    private String websiteCompanyYoutube;
    private String websiteCompanyVk;
    private String websiteCompanyOk;
    private String websiteCompanyTelegram;
    private boolean req_user;
    private boolean followed;
    private boolean isVerified;
    private boolean login_via_google;
    private boolean login_via_vk;
    private List<UserDto>followers;
    private List<UserDto>following;
}