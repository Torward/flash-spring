package ru.lomov.flashbackend.reportuserservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lomov.flashbackend.reportuserservice.entity.UserReport;

import java.util.List;

public interface UserReportRepository extends JpaRepository<UserReport, String> {
    List<UserReport> findByReportedUserId(String userId);
    List<UserReport> findByReportedByUserId(String userId);
}