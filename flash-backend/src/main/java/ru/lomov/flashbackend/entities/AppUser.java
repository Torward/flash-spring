
package ru.lomov.flashbackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "app_user")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;

    private String firstName;
    private String lastName;
    private String fullName;
    private String username;
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
    private boolean isPrivate;
    private boolean isVerified;
    private boolean isActive;
    private boolean isLocked;
    private LocalDateTime lastLogin;
    private boolean requested_usr;
    private boolean login_with_google;
    private boolean login_with_vk;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    @JsonIgnore
    @OneToMany(mappedBy = "appUser")
    private List<Post> posts = new ArrayList<>();



    @ManyToMany
    @JoinTable(
            name = "user_saved_posts",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private List<Post> savedPosts = new ArrayList<>();


    @JsonIgnore
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppLike> appLikes = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppUser> followers = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppUser> following = new ArrayList<>();

    @Embedded
    private Verification verification;


    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private LocalDateTime deletedAt;

}
