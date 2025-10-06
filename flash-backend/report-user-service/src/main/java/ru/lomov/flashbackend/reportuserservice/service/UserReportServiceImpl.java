package ru.lomov.flashbackend.reportuserservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flashbackend.reportuserservice.dto.CreateUserReportDto;
import ru.lomov.flashbackend.reportuserservice.dto.UserReportDto;
import ru.lomov.flashbackend.reportuserservice.dto.UpdateUserReportDto;
import ru.lomov.flashbackend.reportuserservice.entity.UserReport;
import ru.lomov.flashbackend.reportuserservice.repository.UserReportRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserReportServiceImpl implements UserReportService {

    private final UserReportRepository userReportRepository;

    @Override
    public UserReportDto createReport(CreateUserReportDto createUserReportDto) {
        UserReport userReport = new UserReport();
        userReport.setReportedUserId(createUserReportDto.getReportedUserId());
        userReport.setReportedByUserId(createUserReportDto.getReportedByUserId());
        userReport.setReason(createUserReportDto.getReason());
        userReport.setResolved(false);

        UserReport savedReport = userReportRepository.save(userReport);

        UserReportDto response = new UserReportDto();
        response.setReportId(savedReport.getReportId());
        response.setReportedUserId(savedReport.getReportedUserId());
        response.setReportedByUserId(savedReport.getReportedByUserId());
        response.setReason(savedReport.getReason());
        response.setResolved(savedReport.isResolved());
        response.setCreatedAt(savedReport.getCreatedAt());

        return response;
    }

    @Override
    public UserReportDto getReportById(String reportId) {
        UserReport userReport = userReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found with id: " + reportId));

        UserReportDto response = new UserReportDto();
        response.setReportId(userReport.getReportId());
        response.setReportedUserId(userReport.getReportedUserId());
        response.setReportedByUserId(userReport.getReportedByUserId());
        response.setReason(userReport.getReason());
        response.setResolved(userReport.isResolved());
        response.setCreatedAt(userReport.getCreatedAt());

        return response;
    }

    @Override
    public List<UserReportDto> getReportsByUserId(String userId) {
        List<UserReport> reports = userReportRepository.findByReportedUserId(userId);
        return reports.stream()
                .map(report -> {
                    UserReportDto dto = new UserReportDto();
                    dto.setReportId(report.getReportId());
                    dto.setReportedUserId(report.getReportedUserId());
                    dto.setReportedByUserId(report.getReportedByUserId());
                    dto.setReason(report.getReason());
                    dto.setResolved(report.isResolved());
                    dto.setCreatedAt(report.getCreatedAt());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<UserReportDto> getReportsByReportedByUserId(String userId) {
        List<UserReport> reports = userReportRepository.findByReportedByUserId(userId);
        return reports.stream()
                .map(report -> {
                    UserReportDto dto = new UserReportDto();
                    dto.setReportId(report.getReportId());
                    dto.setReportedUserId(report.getReportedUserId());
                    dto.setReportedByUserId(report.getReportedByUserId());
                    dto.setReason(report.getReason());
                    dto.setResolved(report.isResolved());
                    dto.setCreatedAt(report.getCreatedAt());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<UserReportDto> getAllReports() {
        List<UserReport> reports = userReportRepository.findAll();
        return reports.stream()
                .map(report -> {
                    UserReportDto dto = new UserReportDto();
                    dto.setReportId(report.getReportId());
                    dto.setReportedUserId(report.getReportedUserId());
                    dto.setReportedByUserId(report.getReportedByUserId());
                    dto.setReason(report.getReason());
                    dto.setResolved(report.isResolved());
                    dto.setCreatedAt(report.getCreatedAt());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}