package ru.lomov.flash.follow.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "follows", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "target_user_id"})
})
@Data
public class Follow {
    @Id
    @UuidGenerator
    @Column(name = "follow_id", nullable = false, unique = true, updatable = false)
    private String id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "target_user_id", nullable = false)
    private String targetUserId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    // Firebase-compatible fields
    @Column(name = "follower_id")
    private String followerId;

    @Column(name = "following_id")
    private String followingId;

    @Column(name = "follower_count")
    private int followerCount = 0;

    @Column(name = "following_count")
    private int followingCount = 0;

    @Column(name = "is_following")
    private boolean isFollowing = false;

    @Column(name = "is_followed_back")
    private boolean isFollowedBack = false;

    // Firebase-compatible getters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFollowerId() {
        return followerId != null ? followerId : userId;
    }

    public String getFollowingId() {
        return followingId != null ? followingId : targetUserId;
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

    public boolean getIsFollowing() {
        return isFollowing;
    }

    public void setIsFollowing(boolean isFollowing) {
        this.isFollowing = isFollowing;
    }

    public boolean getIsFollowedBack() {
        return isFollowedBack;
    }

    public void setIsFollowedBack(boolean isFollowedBack) {
        this.isFollowedBack = isFollowedBack;
    }
}
