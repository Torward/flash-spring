package ru.lomov.flashbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "comment_id", columnDefinition = "VARCHAR(36)")
    private String commentId;

    @Column(nullable = false)
    private String postId;

    @Column(nullable = false)
    private String userId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column
    private String parentCommentId; // Для вложенных комментариев

    @ElementCollection
    @CollectionTable(name = "comment_replies", joinColumns = @JoinColumn(name = "comment_id"))
    @Column(name = "reply_id")
    private Set<String> replyIds = new HashSet<>(); // Child reply IDs

    @Column(nullable = false)
    private int replyCount = 0; // Number of replies

    @Column(nullable = false)
    private int likeCount = 0;

    @Column(nullable = false)
    private boolean isEdited = false;

    @Column(nullable = false)
    private boolean isDeleted = false;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;
}
