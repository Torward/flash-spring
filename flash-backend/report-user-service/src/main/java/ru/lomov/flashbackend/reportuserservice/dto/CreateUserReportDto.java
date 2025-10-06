package ru.lomov.flashbackend.reportuserservice.dto;

import lombok.Data;

@Data
public class CreateUserReportDto {
    private String reportedUserId;
    private String reportedByUserId;
    private String reason;
}