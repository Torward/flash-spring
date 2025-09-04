package ru.lomov.flashbackend.reportpostservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lomov.flashbackend.reportpostservice.dto.CreatePostReportDto;
import ru.lomov.flashbackend.reportpostservice.dto.PostReportDto;
import ru.lomov.flashbackend.reportpostservice.dto.UpdatePostReportDto;
import ru.lomov.flashbackend.reportpostservice.entity.PostReport;
import ru.lomov.flashbackend.reportpostservice.exception.PostReportNotFoundException;
import ru.lomov.flashbackend.reportpostservice.repository.PostReportRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostReportServiceImpl implements PostReportService {

    private final PostReportRepository postReportRepository;

    @Override
    public PostReportDto createReport(CreatePostReportDto createPostReportDto) {
        // Check if user has already reported this post
        if (postReportRepository.existsByPostIdAndReportedByUserId(
                createPostReportDto.getPostId(),
                createPostReportDto.getReportedByUserId())) {
            throw new IllegalArgumentException("User has already reported this post");
        }

        PostReport postReport = new PostReport();
        postReport.setPostId(createPostReportDto.getPostId());
        postReport.setReportedByUserId(createPostReportDto.getReportedByUserId());
        postReport.setReason(createPostReportDto.getReason());
        postReport.setResolved(false);

        PostReport savedReport = postReportRepository.save(postReport);
        return mapToDto(savedReport);
    }

    @Override
    @Transactional(readOnly = true)
    public PostReportDto getReportById(String reportId) {
        PostReport postReport = postReportRepository.findById(reportId)
                .orElseThrow(() -> new PostReportNotFoundException("Post report not found with id: " + reportId));
        return mapToDto(postReport);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostReportDto> getReportsByPostId(String postId) {
        return postReportRepository.findByPostId(postId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostReportDto> getReportsByUserId(String userId) {
        return postReportRepository.findByReportedByUserId(userId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostReportDto> getAllReports() {
        return postReportRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostReportDto> getUnresolvedReports() {
        return postReportRepository.findUnresolvedReportsOrderByCreatedAtAsc()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PostReportDto> getResolvedReports() {
        return postReportRepository.findByResolved(true)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PostReportDto updateReport(String reportId, UpdatePostReportDto updatePostReportDto) {
        PostReport postReport = postReportRepository.findById(reportId)
                .orElseThrow(() -> new PostReportNotFoundException("Post report not found with id: " + reportId));

        postReport.setResolved(updatePostReportDto.isResolved());

        PostReport updatedReport = postReportRepository.save(postReport);
        return mapToDto(updatedReport);
    }

    @Override
    public void deleteReport(String reportId) {
        if (!postReportRepository.existsById(reportId)) {
            throw new PostReportNotFoundException("Post report not found with id: " + reportId);
        }
        postReportRepository.deleteById(reportId);
    }

    @Override
    @Transactional(readOnly = true)
    public long getReportCountForPost(String postId) {
        return postReportRepository.countByPostId(postId);
    }

    @Override
    @Transactional(readOnly = true)
    public long getUnresolvedReportCount() {
        return postReportRepository.countUnresolvedReports();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean hasUserReportedPost(String postId, String userId) {
        return postReportRepository.existsByPostIdAndReportedByUserId(postId, userId);
    }

    private PostReportDto mapToDto(PostReport postReport) {
        return PostReportDto.builder()
                .reportId(postReport.getReportId())
                .postId(postReport.getPostId())
                .reportedByUserId(postReport.getReportedByUserId())
                .reason(postReport.getReason())
                .resolved(postReport.isResolved())
                .createdAt(postReport.getCreatedAt())
                .build();
    }
}
