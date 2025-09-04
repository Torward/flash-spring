package ru.lomov.flash.report.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lomov.flash.report.dto.ReportDto;
import ru.lomov.flash.report.dto.ReportRequestDto;
import ru.lomov.flash.report.entities.Report;
import ru.lomov.flash.report.repositories.ReportRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;

    @Override
    public ReportDto createReport(ReportRequestDto requestDto) {
        log.info("Creating report for {} with type {} and reason {}",
                requestDto.getReportedId(), requestDto.getReportType(), requestDto.getReason());

        // Check if user has already reported this content
        if (hasUserReported(requestDto.getReporterId(), requestDto.getReportedId(), requestDto.getReportType())) {
            throw new IllegalArgumentException("User has already reported this content");
        }

        Report report = new Report();
        report.setReporterId(requestDto.getReporterId());
        report.setReportedId(requestDto.getReportedId());
        report.setReportType(requestDto.getReportType());
        report.setReason(requestDto.getReason());
        report.setDescription(requestDto.getDescription());
        report.setStatus(Report.ReportStatus.PENDING);

        Report savedReport = reportRepository.save(report);
        log.info("Report created with ID: {}", savedReport.getId());

        return convertToDto(savedReport);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ReportDto> getReportById(String id) {
        return reportRepository.findById(id).map(this::convertToDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportDto> getAllReports() {
        return reportRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportDto> getReportsByReporter(String reporterId) {
        return reportRepository.findByReporterId(reporterId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportDto> getReportsByReportedId(String reportedId) {
        return reportRepository.findByReportedId(reportedId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportDto> getReportsByType(Report.ReportType reportType) {
        return reportRepository.findByReportType(reportType).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportDto> getReportsByStatus(Report.ReportStatus status) {
        return reportRepository.findByStatus(status).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReportDto> getPendingReports() {
        return reportRepository.findPendingReports().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ReportDto updateReportStatus(String id, Report.ReportStatus status, String moderatorId, String moderatorNotes) {
        log.info("Updating report {} status to {} by moderator {}", id, status, moderatorId);

        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Report not found with id: " + id));

        report.setStatus(status);
        report.setModeratorId(moderatorId);
        report.setModeratorNotes(moderatorNotes);
        report.setReviewedAt(LocalDateTime.now());

        Report updatedReport = reportRepository.save(report);
        return convertToDto(updatedReport);
    }

    @Override
    public void deleteReport(String id) {
        log.info("Deleting report with ID: {}", id);
        if (!reportRepository.existsById(id)) {
            throw new IllegalArgumentException("Report not found with id: " + id);
        }
        reportRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long getPendingReportCount(String reportedId, Report.ReportType reportType) {
        return reportRepository.countPendingReportsByReportedIdAndType(reportedId, reportType);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasUserReported(String reporterId, String reportedId, Report.ReportType reportType) {
        List<Report> existingReports = reportRepository.findByReportedIdAndType(reportedId, reportType);
        return existingReports.stream()
                .anyMatch(report -> report.getReporterId().equals(reporterId));
    }

    private ReportDto convertToDto(Report report) {
        ReportDto dto = new ReportDto();
        dto.setId(report.getId());
        dto.setReporterId(report.getReporterId());
        dto.setReportedId(report.getReportedId());
        dto.setReportType(report.getReportType());
        dto.setReason(report.getReason());
        dto.setDescription(report.getDescription());
        dto.setStatus(report.getStatus());
        dto.setModeratorId(report.getModeratorId());
        dto.setModeratorNotes(report.getModeratorNotes());
        dto.setReviewedAt(report.getReviewedAt());
        dto.setCreatedAt(report.getCreatedAt());
        dto.setUpdatedAt(report.getUpdatedAt());
        return dto;
    }
}
