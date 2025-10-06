package ru.lomov.flashbackend.reportreelservice.service;

import ru.lomov.flashbackend.reportreelservice.dto.CreateReelReportDto;
import ru.lomov.flashbackend.reportreelservice.dto.ReelReportDto;
import ru.lomov.flashbackend.reportreelservice.dto.UpdateReelReportDto;

import java.util.List;

public interface ReelReportService {
    ReelReportDto createReport(CreateReelReportDto createReelReportDto);
    ReelReportDto getReportById(String reportId);
    List<ReelReportDto> getReportsByReelId(String reelId);
    List<ReelReportDto> getReportsByUserId(String userId);
    List<ReelReportDto> getAllReports();
}