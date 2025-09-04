package ru.lomov.flashbackend.reportpostservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flashbackend.reportpostservice.dto.ApiResponse;
import ru.lomov.flashbackend.reportpostservice.dto.CreatePostReportDto;
import ru.lomov.flashbackend.reportpostservice.dto.PostReportDto;
import ru.lomov.flashbackend.reportpostservice.dto.UpdatePostReportDto;
import ru.lomov.flashbackend.reportpostservice.service.PostReportService;

import java.util.List;

@RestController
@RequestMapping("/api/post-reports")
@RequiredArgsConstructor
public class PostReportController {

    private final PostReportService postReportService;

    @PostMapping
    public ResponseEntity<PostReportDto> createReport(@RequestBody CreatePostReportDto createPostReportDto) {
        PostReportDto postReportDto = postReportService.createReport(createPostReportDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(postReportDto);
    }

    @GetMapping("/{reportId}")
    public ResponseEntity<PostReportDto> getReport(@PathVariable String reportId) {
        PostReportDto postReportDto = postReportService.getReportById(reportId);
        return ResponseEntity.ok(postReportDto);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<PostReportDto>> getReportsByPostId(@PathVariable String postId) {
        List<PostReportDto> reports = postReportService.getReportsByPostId(postId);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostReportDto>> getReportsByUserId(@PathVariable String userId) {
        List<PostReportDto> reports = postReportService.getReportsByUserId(userId);
        return ResponseEntity.ok(reports);
    }

    @GetMapping
    public ResponseEntity<List<PostReportDto>> getAllReports() {
        List<PostReportDto> reports = postReportService.getAllReports();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/unresolved")
    public ResponseEntity<List<PostReportDto>> getUnresolvedReports() {
        List<PostReportDto> reports = postReportService.getUnresolvedReports();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/resolved")
    public ResponseEntity<List<PostReportDto>> getResolvedReports() {
        List<PostReportDto> reports = postReportService.getResolvedReports();
        return ResponseEntity.ok(reports);
    }

    @PutMapping("/{reportId}")
    public ResponseEntity<PostReportDto> updateReport(
            @PathVariable String reportId,
            @RequestBody UpdatePostReportDto updatePostReportDto) {
        PostReportDto updatedReport = postReportService.updateReport(reportId, updatePostReportDto);
        return ResponseEntity.ok(updatedReport);
    }

    @DeleteMapping("/{reportId}")
    public ResponseEntity<ApiResponse> deleteReport(@PathVariable String reportId) {
        postReportService.deleteReport(reportId);
        return ResponseEntity.ok(new ApiResponse(true, "Report deleted successfully", null));
    }

    @GetMapping("/post/{postId}/count")
    public ResponseEntity<Long> getReportCountForPost(@PathVariable String postId) {
        long count = postReportService.getReportCountForPost(postId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/unresolved/count")
    public ResponseEntity<Long> getUnresolvedReportCount() {
        long count = postReportService.getUnresolvedReportCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> hasUserReportedPost(
            @RequestParam String postId,
            @RequestParam String userId) {
        boolean hasReported = postReportService.hasUserReportedPost(postId, userId);
        return ResponseEntity.ok(hasReported);
    }
}
