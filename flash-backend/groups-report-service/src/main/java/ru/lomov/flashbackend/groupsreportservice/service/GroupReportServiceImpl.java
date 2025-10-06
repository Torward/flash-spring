package ru.lomov.flashbackend.groupsreportservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flashbackend.groupsreportservice.dto.CreateGroupReportDto;
import ru.lomov.flashbackend.groupsreportservice.dto.GroupReportDto;
import ru.lomov.flashbackend.groupsreportservice.entity.GroupReport;
import ru.lomov.flashbackend.groupsreportservice.repository.GroupReportRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupReportServiceImpl implements GroupReportService {

    private final GroupReportRepository groupReportRepository;

    @Override
    public GroupReportDto createReport(CreateGroupReportDto createGroupReportDto) {
        if (groupReportRepository.existsByGroupIdAndReportedByUserId(
                createGroupReportDto.getGroupId(),
                createGroupReportDto.getReportedByUserId())) {
            throw new IllegalArgumentException("User has already reported this group");
        }

        GroupReport groupReport = new GroupReport();
        groupReport.setGroupId(createGroupReportDto.getGroupId());
        groupReport.setReportedByUserId(createGroupReportDto.getReportedByUserId());
        groupReport.setReason(createGroupReportDto.getReason());
        groupReport.setResolved(false);

        GroupReport savedReport = groupReportRepository.save(groupReport);
        return mapToDto(savedReport);
    }

    @Override
    public GroupReportDto getReportById(String reportId) {
        GroupReport groupReport = groupReportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Group report not found with id: " + reportId));
        return mapToDto(groupReport);
    }

    @Override
    public List<GroupReportDto> getReportsByGroupId(String groupId) {
        return groupReportRepository.findByGroupId(groupId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GroupReportDto> getReportsByUserId(String userId) {
        return groupReportRepository.findByReportedByUserId(userId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GroupReportDto> getAllReports() {
        return groupReportRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private GroupReportDto mapToDto(GroupReport groupReport) {
        return GroupReportDto.builder()
                .reportId(groupReport.getReportId())
                .groupId(groupReport.getGroupId())
                .reportedByUserId(groupReport.getReportedByUserId())
                .reason(groupReport.getReason())
                .resolved(groupReport.isResolved())
                .createdAt(groupReport.getCreatedAt())
                .build();
    }
}