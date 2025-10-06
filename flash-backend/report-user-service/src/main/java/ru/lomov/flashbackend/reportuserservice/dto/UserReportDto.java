package ru.lomov.flashbackend.reportuserservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserReportDto {
    private String reportId;
    private String reportedUserId;
    private String reportedByUserId;
    private String reason;
    private boolean resolved;
    private LocalDateTime createdAt;
}