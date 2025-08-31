package ru.lomov.flashbackend.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "app_users")
@Data
@NoArgsConstructor
public class AppUser {
    @Id
    @UuidGenerator
    @Column(name = "user_id", nullable = false, unique = true, updatable = false)
    private String userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(name = "photo")
    private String profilePicture;

    @Column(name = "cover")
    private String cover;

    @Column(name = "bio")
    private String bio;

    @Column(name = "website")
    private String website;

    @Column(name = "phone")
    private String phoneNumber;

    @Column(name = "location")
    private String location;

    @Column(nullable = false)
    private String status = "offline"; // online, offline

    @Column(nullable = false)
    private boolean verified = false;

    @Column(nullable = false)
    private boolean isPrivate = false;

    @Column(nullable = false)
    private boolean isActive = true;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    // Firebase-compatible collections
    @ElementCollection
    @CollectionTable(name = "user_blocked_users", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "blocked_user_id")
    private Set<String> blockedUsers = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "user_high_stories", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "story_id")
    private Set<String> highStories = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "user_notifications", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "notification_id")
    private Set<String> notifications = new HashSet<>();

    @Column(name = "notification_count", nullable = false)
    private int notificationCount = 0;

    @ElementCollection
    @CollectionTable(name = "user_following", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "following_user_id")
    private Set<String> following = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "user_followers", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "follower_user_id")
    private Set<String> followers = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "user_interests", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "interest")
    private Set<String> interests = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "user_skills", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "skill")
    private Set<String> skills = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "user_social_links", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "social_link")
    private Set<String> socialLinks = new HashSet<>();

    // Firebase Count field equivalent
    @Column(name = "follower_count", nullable = false)
    private int followerCount = 0;

    @Column(name = "following_count", nullable = false)
    private int followingCount = 0;

    @Column(name = "post_count", nullable = false)
    private int postCount = 0;

    // Additional Firebase-compatible fields
    @Column(name = "login_count", nullable = false)
    private int loginCount = 0;

    @Column(name = "profile_views", nullable = false)
    private int profileViews = 0;

    // Business-related fields
    private String companyName;
    private String jobTitle;
    private String industry;
    private String businessType;
    private String taxId;
    private String businessRegistrationNumber;

    // Preferences and settings
    private String language = "ru";
    private String timezone = "Europe/Moscow";
    private boolean emailNotifications = true;
    private boolean pushNotifications = true;
    private boolean smsNotifications = false;

    // Security and authentication
    private boolean twoFactorEnabled = false;
    private String twoFactorSecret;
    private LocalDateTime lastLogin;
    private String lastLoginIp;

    // Subscription and monetization
    private String subscriptionTier = "FREE";
    private LocalDateTime subscriptionExpiry;
    private boolean isPremium = false;
    private String stripeCustomerId;
    private String paymentMethodId;

    // GDPR and compliance
    private boolean termsAccepted = false;
    private boolean privacyPolicyAccepted = false;
    private boolean marketingConsent = false;
    private LocalDateTime dataProcessingConsentDate;

    // Additional metadata
    private String deviceToken;
    private String appVersion;
    private String operatingSystem;
    private String browserInfo;

    // Firebase-compatible getters and setters
    public String getId() {
        return userId;
    }

    public void setId(String userId) {
        this.userId = userId;
    }

    public String getPhoto() {
        return profilePicture;
    }

    public void setPhoto(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getPhone() {
        return phoneNumber;
    }

    public void setPhone(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Explicit getter for boolean field to avoid Lombok issues
    public boolean getIsPrivate() {
        return isPrivate;
    }

    // Firebase-compatible method names
    public Set<String> getBlockedUsers() {
        return blockedUsers;
    }

    public void setBlockedUsers(Set<String> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public Set<String> getHigh() {
        return highStories;
    }

    public void setHigh(Set<String> highStories) {
        this.highStories = highStories;
    }

    public Set<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<String> notifications) {
        this.notifications = notifications;
    }

    public int getCount() {
        return notificationCount;
    }

    public void setCount(int notificationCount) {
        this.notificationCount = notificationCount;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Explicit getters for collections that Lombok might not generate correctly
    public Set<String> getFollowing() {
        return following;
    }

    public void setFollowing(Set<String> following) {
        this.following = following;
    }

    public Set<String> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<String> followers) {
        this.followers = followers;
    }
}
