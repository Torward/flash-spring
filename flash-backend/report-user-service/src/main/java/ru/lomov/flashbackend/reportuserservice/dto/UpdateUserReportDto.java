package ru.lomov.flashbackend.reportuserservice.dto;

import lombok.Data;

@Data
public class UpdateUserReportDto {
    private String reason;
    private boolean resolved;
}