package ru.lomov.flashbackend.reportreelservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flashbackend.reportreelservice.dto.ApiResponse;
import ru.lomov.flashbackend.reportreelservice.dto.CreateReelReportDto;
import ru.lomov.flashbackend.reportreelservice.dto.ReelReportDto;
import ru.lomov.flashbackend.reportreelservice.dto.UpdateReelReportDto;
import ru.lomov.flashbackend.reportreelservice.service.ReelReportService;

import java.util.List;

@RestController
@RequestMapping("/api/reel-reports")
@RequiredArgsConstructor
public class ReelReportController {

    private final ReelReportService reelReportService;

    @PostMapping
    public ResponseEntity<ReelReportDto> createReport(@RequestBody CreateReelReportDto createReelReportDto) {
        ReelReportDto reelReportDto = reelReportService.createReport(createReelReportDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reelReportDto);
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<ReelReportDto> getReport(@PathVariable String reportId) {
        ReelReportDto reelReportDto = reelReportService.getReportById(reportId);
        return ResponseEntity.ok(reelReportDto);
    }

    @GetMapping("/reel/{reelId}")
    public ResponseEntity<List<ReelReportDto>> getReportsByReelId(@PathVariable String reelId) {
        List<ReelReportDto> reports = reelReportService.getReportsByReelId(reelId);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReelReportDto>> getReportsByUserId(@PathVariable String userId) {
        List<ReelReportDto> reports = reelReportService.getReportsByUserId(userId);
        return ResponseEntity.ok(reports);
    }

    @GetMapping
    public ResponseEntity<List<ReelReportDto>> getAllReports() {
        List<ReelReportDto> reports = reelReportService.getAllReports();
        return ResponseEntity.ok(reports);
    }
}