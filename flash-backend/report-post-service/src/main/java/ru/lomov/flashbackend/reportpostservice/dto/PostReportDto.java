package ru.lomov.flashbackend.reportpostservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostReportDto {
    private String reportId;
    private String postId;
    private String reportedByUserId;
    private String reason;
    private boolean resolved;
    private LocalDateTime createdAt;
}
