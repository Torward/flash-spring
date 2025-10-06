package ru.lomov.flashbackend.reportuserservice.service;

import ru.lomov.flashbackend.reportuserservice.dto.CreateUserReportDto;
import ru.lomov.flashbackend.reportuserservice.dto.UserReportDto;
import ru.lomov.flashbackend.reportuserservice.dto.UpdateUserReportDto;

import java.util.List;

public interface UserReportService {
    UserReportDto createReport(CreateUserReportDto createUserReportDto);
    UserReportDto getReportById(String reportId);
    List<UserReportDto> getReportsByUserId(String userId);
    List<UserReportDto> getReportsByReportedByUserId(String userId);
    List<UserReportDto> getAllReports();
}