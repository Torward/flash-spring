package ru.lomov.flash.report.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id
    @UuidGenerator
    private String id;

    @Column(nullable = false)
    private String reporterId; // ID of the user who reported

    @Column(nullable = false)
    private String reportedId; // ID of the reported content/user

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportType reportType; // POST, REEL, USER, PRODUCT, GROUP

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportReason reason; // SPAM, HARASSMENT, INAPPROPRIATE, VIOLENCE, etc.

    @Column(length = 1000)
    private String description; // Additional details from reporter

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status; // PENDING, REVIEWED, RESOLVED, DISMISSED

    @Column
    private String moderatorId; // ID of moderator who reviewed

    @Column(length = 1000)
    private String moderatorNotes; // Notes from moderator

    @Column
    private LocalDateTime reviewedAt; // When the report was reviewed

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public enum ReportType {
        POST,
        REEL,
        USER,
        PRODUCT,
        GROUP
    }

    public enum ReportReason {
        SPAM,
        HARASSMENT,
        INAPPROPRIATE_CONTENT,
        VIOLENCE,
        HATE_SPEECH,
        MISLEADING,
        COPYRIGHT_VIOLATION,
        OTHER
    }

    public enum ReportStatus {
        PENDING,
        UNDER_REVIEW,
        RESOLVED,
        DISMISSED
    }
}
