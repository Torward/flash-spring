package ru.lomov.flashbackend.reportpostservice.service;

import ru.lomov.flashbackend.reportpostservice.dto.CreatePostReportDto;
import ru.lomov.flashbackend.reportpostservice.dto.PostReportDto;
import ru.lomov.flashbackend.reportpostservice.dto.UpdatePostReportDto;

import java.util.List;

public interface PostReportService {

    PostReportDto createReport(CreatePostReportDto createPostReportDto);

    PostReportDto getReportById(String reportId);

    List<PostReportDto> getReportsByPostId(String postId);

    List<PostReportDto> getReportsByUserId(String userId);

    List<PostReportDto> getAllReports();

    List<PostReportDto> getUnresolvedReports();

    List<PostReportDto> getResolvedReports();

    PostReportDto updateReport(String reportId, UpdatePostReportDto updatePostReportDto);

    void deleteReport(String reportId);

    long getReportCountForPost(String postId);

    long getUnresolvedReportCount();

    boolean hasUserReportedPost(String postId, String userId);
}
