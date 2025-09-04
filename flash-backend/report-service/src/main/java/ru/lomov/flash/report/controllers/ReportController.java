package ru.lomov.flash.report.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flash.report.dto.ReportDto;
import ru.lomov.flash.report.dto.ReportRequestDto;
import ru.lomov.flash.report.entities.Report;
import ru.lomov.flash.report.services.ReportService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Reports", description = "API for content reporting and moderation")
public class ReportController {

    private final ReportService reportService;

    // Firebase-compatible endpoints for reporting content

    @PostMapping("/reportPost/{postId}")
    @Operation(summary = "Report a post")
    public ResponseEntity<ReportDto> reportPost(@PathVariable String postId,
                                               @Valid @RequestBody ReportRequestDto requestDto) {
        log.info("Reporting post: {}", postId);
        requestDto.setReportedId(postId);
        requestDto.setReportType(Report.ReportType.POST);
        ReportDto report = reportService.createReport(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(report);
    }

    @PostMapping("/reportReel/{reelId}")
    @Operation(summary = "Report a reel")
    public ResponseEntity<ReportDto> reportReel(@PathVariable String reelId,
                                               @Valid @RequestBody ReportRequestDto requestDto) {
        log.info("Reporting reel: {}", reelId);
        requestDto.setReportedId(reelId);
        requestDto.setReportType(Report.ReportType.REEL);
        ReportDto report = reportService.createReport(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(report);
    }

    @PostMapping("/reportUser/{userId}")
    @Operation(summary = "Report a user")
    public ResponseEntity<ReportDto> reportUser(@PathVariable String userId,
                                               @Valid @RequestBody ReportRequestDto requestDto) {
        log.info("Reporting user: {}", userId);
        requestDto.setReportedId(userId);
        requestDto.setReportType(Report.ReportType.USER);
        ReportDto report = reportService.createReport(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(report);
    }

    @PostMapping("/reportProduct/{productId}")
    @Operation(summary = "Report a product")
    public ResponseEntity<ReportDto> reportProduct(@PathVariable String productId,
                                                  @Valid @RequestBody ReportRequestDto requestDto) {
        log.info("Reporting product: {}", productId);
        requestDto.setReportedId(productId);
        requestDto.setReportType(Report.ReportType.PRODUCT);
        ReportDto report = reportService.createReport(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(report);
    }

    @PostMapping("/groupsReport/{groupId}")
    @Operation(summary = "Report a group")
    public ResponseEntity<ReportDto> reportGroup(@PathVariable String groupId,
                                                @Valid @RequestBody ReportRequestDto requestDto) {
        log.info("Reporting group: {}", groupId);
        requestDto.setReportedId(groupId);
        requestDto.setReportType(Report.ReportType.GROUP);
        ReportDto report = reportService.createReport(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(report);
    }

    // Alternative endpoint for user reports (Firebase compatibility)
    @GetMapping("/userReport/{userId}")
    @Operation(summary = "Get reports for a user")
    public ResponseEntity<List<ReportDto>> getUserReports(@PathVariable String userId) {
        List<ReportDto> reports = reportService.getReportsByReportedId(userId);
        return ResponseEntity.ok(reports);
    }

    // Admin/Moderator endpoints

    @GetMapping("/reports")
    @Operation(summary = "Get all reports")
    public ResponseEntity<List<ReportDto>> getAllReports() {
        List<ReportDto> reports = reportService.getAllReports();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/reports/pending")
    @Operation(summary = "Get pending reports")
    public ResponseEntity<List<ReportDto>> getPendingReports() {
        List<ReportDto> reports = reportService.getPendingReports();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/reports/{id}")
    @Operation(summary = "Get report by ID")
    public ResponseEntity<ReportDto> getReportById(@PathVariable String id) {
        return reportService.getReportById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/reports/{id}/status")
    @Operation(summary = "Update report status")
    public ResponseEntity<ReportDto> updateReportStatus(@PathVariable String id,
                                                        @RequestBody Map<String, Object> statusUpdate) {
        String statusStr = (String) statusUpdate.get("status");
        String moderatorId = (String) statusUpdate.get("moderatorId");
        String moderatorNotes = (String) statusUpdate.get("moderatorNotes");

        Report.ReportStatus status = Report.ReportStatus.valueOf(statusStr.toUpperCase());
        ReportDto updatedReport = reportService.updateReportStatus(id, status, moderatorId, moderatorNotes);
        return ResponseEntity.ok(updatedReport);
    }

    @DeleteMapping("/reports/{id}")
    @Operation(summary = "Delete report")
    public ResponseEntity<Void> deleteReport(@PathVariable String id) {
        reportService.deleteReport(id);
        return ResponseEntity.noContent().build();
    }

    // Utility endpoints

    @GetMapping("/reports/count/{reportedId}/{reportType}")
    @Operation(summary = "Get pending report count for content")
    public ResponseEntity<Map<String, Long>> getPendingReportCount(@PathVariable String reportedId,
                                                                  @PathVariable String reportType) {
        Report.ReportType type = Report.ReportType.valueOf(reportType.toUpperCase());
        long count = reportService.getPendingReportCount(reportedId, type);
        return ResponseEntity.ok(Map.of("count", count));
    }

    @GetMapping("/reports/check/{reporterId}/{reportedId}/{reportType}")
    @Operation(summary = "Check if user has already reported content")
    public ResponseEntity<Map<String, Boolean>> hasUserReported(@PathVariable String reporterId,
                                                               @PathVariable String reportedId,
                                                               @PathVariable String reportType) {
        Report.ReportType type = Report.ReportType.valueOf(reportType.toUpperCase());
        boolean hasReported = reportService.hasUserReported(reporterId, reportedId, type);
        return ResponseEntity.ok(Map.of("hasReported", hasReported));
    }
}
