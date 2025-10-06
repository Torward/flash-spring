package ru.lomov.flashbackend.reportreelservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flashbackend.reportreelservice.dto.CreateReelReportDto;
import ru.lomov.flashbackend.reportreelservice.dto.ReelReportDto;
import ru.lomov.flashbackend.reportreelservice.dto.UpdateReelReportDto;
import ru.lomov.flashbackend.reportreelservice.entity.ReelReport;
import ru.lomov.flashbackend.reportreelservice.repository.ReelReportRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReelReportServiceImpl implements ReelReportService {

    private final ReelReportRepository reelReportRepository;

    @Override
    public ReelReportDto createReport(CreateReelReportDto createReelReportDto) {
        if (reelReportRepository.existsByReelIdAndReportedByUserId(
                createReelReportDto.getReelId(),
                createReelReportDto.getReportedByUserId())) {
            throw new IllegalArgumentException("User has already reported this reel");
        }

        ReelReport reelReport = new ReelReport();
        reelReport.setReelId(createReelReportDto.getReelId());
        reelReport.setReportedByUserId(createReelReportDto.getReportedByUserId());
        reelReport.setReason(createReelReportDto.getReason());
        reelReport.setResolved(false);

        ReelReport savedReport = reelReportRepository.save(reelReport);
        return mapToDto(savedReport);
    }

    @Override
    public ReelReportDto getReportById(String reportId) {
        ReelReport reelReport = reelReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Reel report not found with id: " + reportId));
        return mapToDto(reelReport);
    }

    @Override
    public List<ReelReportDto> getReportsByReelId(String reelId) {
        return reelReportRepository.findByReelId(reelId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReelReportDto> getReportsByUserId(String userId) {
        return reelReportRepository.findByReportedByUserId(userId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReelReportDto> getAllReports() {
        return reelReportRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ReelReportDto mapToDto(ReelReport reelReport) {
        return ReelReportDto.builder()
                .reportId(reelReport.getReportId())
                .reelId(reelReport.getReelId())
                .reportedByUserId(reelReport.getReportedByUserId())
                .reason(reelReport.getReason())
                .resolved(reelReport.isResolved())
                .createdAt(reelReport.getCreatedAt())
                .build();
    }
}