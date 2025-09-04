package ru.lomov.flash.report.dto;

import lombok.Data;
import ru.lomov.flash.report.entities.Report;

import java.time.LocalDateTime;

@Data
public class ReportDto {
    private String id;
    private String reporterId;
    private String reportedId;
    private Report.ReportType reportType;
    private Report.ReportReason reason;
    private String description;
    private Report.ReportStatus status;
    private String moderatorId;
    private String moderatorNotes;
    private LocalDateTime reviewedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
