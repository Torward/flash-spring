package ru.lomov.flashbackend.reportpostservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "post_reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostReport {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String reportId;

    @Column(nullable = false)
    private String postId;

    @Column(nullable = false)
    private String reportedByUserId;

    @Column(nullable = false, length = 1000)
    private String reason;

    @Column(nullable = false)
    private boolean resolved;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
