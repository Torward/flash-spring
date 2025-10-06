package ru.lomov.flashbackend.reportreelservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReelReportDto {
    private String reportId;
    private String reelId;
    private String reportedByUserId;
    private String reason;
    private boolean resolved;
    private LocalDateTime createdAt;
}