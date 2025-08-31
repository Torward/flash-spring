package ru.lomov.flashbackend.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
public class Post {
    @Id
    @UuidGenerator
    private String postId;

    @Column(nullable = false)
    private String userId;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ElementCollection
    @CollectionTable(name = "post_media", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "media_url")
    private Set<String> mediaUrls = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "post_hashtags", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "hashtag")
    private Set<String> hashtags = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "post_mentions", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "mentioned_user_id")
    private Set<String> mentionedUserIds = new HashSet<>();

    @Column(nullable = false)
    private String visibility = "PUBLIC"; // PUBLIC, PRIVATE, FRIENDS_ONLY

    @Column(nullable = false)
    private boolean commentsEnabled = true;

    @Column(nullable = false)
    private boolean likesEnabled = true;

    @Column(nullable = false)
    private boolean sharingEnabled = true;

    @Column(nullable = false)
    private boolean isPinned = false;

    @Column(nullable = false)
    private boolean isArchived = false;

    @Column(nullable = false)
    private boolean isDeleted = false;

    @Column(nullable = false)
    private String status = "ACTIVE"; // ACTIVE, PENDING, REJECTED, FLAGGED

    @Column
    private String location;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column
    private String language = "ru";

    @Column
    private String sentiment; // POSITIVE, NEGATIVE, NEUTRAL

    @Column
    private Double sentimentScore;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime scheduledAt;

    @Column
    private LocalDateTime publishedAt;

    @Column
    private LocalDateTime deletedAt;

    // Analytics fields
    @Column(nullable = false)
    private int likeCount = 0;

    @Column(nullable = false)
    private int commentCount = 0;

    @Column(nullable = false)
    private int shareCount = 0;

    @Column(nullable = false)
    private int viewCount = 0;

    @Column(nullable = false)
    private int saveCount = 0;

    @Column(nullable = false)
    private int clickCount = 0;

    @Column(nullable = false)
    private int impressionCount = 0;

    @Column(nullable = false)
    private int reachCount = 0;

    @Column(nullable = false)
    private int engagementCount = 0;

    // Monetization fields
    @Column(nullable = false)
    private boolean isSponsored = false;

    @Column
    private String sponsorId;

    @Column
    private String campaignId;

    @Column
    private Double cpmRate;

    @Column
    private Double revenueGenerated = 0.0;

    // SEO and discovery
    @Column
    private String metaTitle;

    @Column(columnDefinition = "TEXT")
    private String metaDescription;

    @ElementCollection
    @CollectionTable(name = "post_keywords", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "keyword")
    private Set<String> keywords = new HashSet<>();

    @Column
    private String ogImage;

    @Column
    private String canonicalUrl;

    // Content moderation
    @Column(nullable = false)
    private boolean isModerated = false;

    @Column
    private LocalDateTime moderatedAt;

    @Column
    private String moderationStatus; // APPROVED, REJECTED, PENDING

    @Column
    private String moderationReason;

    @Column
    private Double moderationScore;

    // Versioning and history
    @Column(nullable = false)
    private int version = 1;

    @Column
    private String originalPostId; // For reposts/shared posts

    @Column
    private String postType = "STANDARD"; // STANDARD, STORY, REEL, LIVE, POLL, QUIZ

    // Poll-specific fields
    @ElementCollection
    @CollectionTable(name = "post_poll_options", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "poll_option")
    private Set<String> pollOptions = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "post_poll_votes", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "vote_count")
    private Set<Integer> pollVotes = new HashSet<>();

    @Column
    private LocalDateTime pollEndsAt;

    // Live stream fields
    @Column
    private String streamUrl;

    @Column
    private String streamKey;

    @Column
    private Boolean isLive = false;

    @Column
    private Integer liveViewers = 0;

    @Column
    private LocalDateTime liveStartedAt;

    @Column
    private LocalDateTime liveEndedAt;

    // Additional metadata
    @Column
    private String deviceInfo;

    @Column
    private String appVersion;

    @Column
    private String ipAddress;

    @Column
    private String userAgent;
}
