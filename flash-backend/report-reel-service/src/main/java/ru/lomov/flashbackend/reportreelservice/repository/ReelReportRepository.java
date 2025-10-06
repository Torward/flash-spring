package ru.lomov.flashbackend.reportreelservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lomov.flashbackend.reportreelservice.entity.ReelReport;

import java.util.List;

public interface ReelReportRepository extends JpaRepository<ReelReport, String> {
    List<ReelReport> findByReelId(String reelId);
    List<ReelReport> findByReportedByUserId(String userId);
}