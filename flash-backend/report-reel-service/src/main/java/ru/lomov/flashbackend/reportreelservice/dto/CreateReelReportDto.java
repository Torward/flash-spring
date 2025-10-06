package ru.lomov.flashbackend.reportreelservice.dto;

import lombok.Data;

@Data
public class CreateReelReportDto {
    private String reelId;
    private String reportedByUserId;
    private String reason;
}