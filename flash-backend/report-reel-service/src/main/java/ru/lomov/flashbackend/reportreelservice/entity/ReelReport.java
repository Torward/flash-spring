package ru.lomov.flashbackend.reportreelservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reel_reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReelReport {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String reportId;

    @Column(nullable = false)
    private String reelId;

    @Column(nullable = false)
    private String reportedByUserId;

    @Column(nullable = false, length = 1000)
    private String reason;

    @Column(nullable = false)
    private boolean resolved;

    @CreationTimestamp
    private LocalDateTime createdAt;
}