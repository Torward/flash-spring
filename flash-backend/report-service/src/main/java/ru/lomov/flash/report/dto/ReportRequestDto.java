package ru.lomov.flash.report.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.lomov.flash.report.entities.Report;

@Data
public class ReportRequestDto {

    @NotBlank(message = "Reporter ID is required")
    private String reporterId;

    @NotBlank(message = "Reported ID is required")
    private String reportedId;

    @NotNull(message = "Report type is required")
    private Report.ReportType reportType;

    @NotNull(message = "Report reason is required")
    private Report.ReportReason reason;

    private String description;
}
