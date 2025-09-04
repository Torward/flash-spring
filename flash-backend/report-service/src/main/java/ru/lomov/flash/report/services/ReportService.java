package ru.lomov.flash.report.services;

import ru.lomov.flash.report.dto.ReportDto;
import ru.lomov.flash.report.dto.ReportRequestDto;
import ru.lomov.flash.report.entities.Report;

import java.util.List;
import java.util.Optional;

public interface ReportService {

    ReportDto createReport(ReportRequestDto requestDto);

    Optional<ReportDto> getReportById(String id);

    List<ReportDto> getAllReports();

    List<ReportDto> getReportsByReporter(String reporterId);

    List<ReportDto> getReportsByReportedId(String reportedId);

    List<ReportDto> getReportsByType(Report.ReportType reportType);

    List<ReportDto> getReportsByStatus(Report.ReportStatus status);

    List<ReportDto> getPendingReports();

    ReportDto updateReportStatus(String id, Report.ReportStatus status, String moderatorId, String moderatorNotes);

    void deleteReport(String id);

    long getPendingReportCount(String reportedId, Report.ReportType reportType);

    boolean hasUserReported(String reporterId, String reportedId, Report.ReportType reportType);
}
