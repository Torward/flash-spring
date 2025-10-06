package ru.lomov.flash.follow.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
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
    // Firebase-compatible getters

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

    @Getter
    @Setter
    @Column(name = "follower_count")
    private int followerCount = 0;

    @Getter
    @Setter
    @Column(name = "following_count")
    private int followingCount = 0;

    @Setter
    @Column(name = "is_following")
    private boolean isFollowing = false;

    @Setter
    @Column(name = "is_followed_back")
    private boolean isFollowedBack = false;

    public String getFollowerId() {
        return followerId != null ? followerId : userId;
    }

    public String getFollowingId() {
        return followingId != null ? followingId : targetUserId;
    }

    public boolean getIsFollowing() {
        return isFollowing;
    }

    public boolean getIsFollowedBack() {
        return isFollowedBack;
    }

}
