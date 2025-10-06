package ru.lomov.flashbackend.reportreelservice.dto;

import lombok.Data;

@Data
public class UpdateReelReportDto {
    private String reason;
    private boolean resolved;
}