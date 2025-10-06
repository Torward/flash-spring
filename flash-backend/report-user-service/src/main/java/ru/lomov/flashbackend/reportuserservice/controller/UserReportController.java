package ru.lomov.flashbackend.reportuserservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flashbackend.reportuserservice.dto.ApiResponse;
import ru.lomov.flashbackend.reportuserservice.dto.CreateUserReportDto;
import ru.lomov.flashbackend.reportuserservice.dto.UserReportDto;
import ru.lomov.flashbackend.reportuserservice.dto.UpdateUserReportDto;
import ru.lomov.flashbackend.reportuserservice.service.UserReportService;

import java.util.List;

@RestController
@RequestMapping("/api/user-reports")
@RequiredArgsConstructor
public class UserReportController {

    private final UserReportService userReportService;

    @PostMapping
    public ResponseEntity<UserReportDto> createReport(@RequestBody CreateUserReportDto createUserReportDto) {
        UserReportDto userReportDto = userReportService.createReport(createUserReportDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userReportDto);
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<UserReportDto> getReport(@PathVariable String reportId) {
        UserReportDto userReportDto = userReportService.getReportById(reportId);
        return ResponseEntity.ok(userReportDto);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserReportDto>> getReportsByUserId(@PathVariable String userId) {
        List<UserReportDto> reports = userReportService.getReportsByUserId(userId);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/reported-by/{userId}")
    public ResponseEntity<List<UserReportDto>> getReportsByReportedByUserId(@PathVariable String userId) {
        List<UserReportDto> reports = userReportService.getReportsByReportedByUserId(userId);
        return ResponseEntity.ok(reports);
    }

    @GetMapping
    public ResponseEntity<List<UserReportDto>> getAllReports() {
        List<UserReportDto> reports = userReportService.getAllReports();
        return ResponseEntity.ok(reports);
    }
}