package ru.lomov.flash.report.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lomov.flash.report.entities.Report;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, String> {

    List<Report> findByReporterId(String reporterId);

    List<Report> findByReportedId(String reportedId);

    List<Report> findByReportType(Report.ReportType reportType);

    List<Report> findByStatus(Report.ReportStatus status);

    @Query("SELECT r FROM Report r WHERE r.reportedId = :reportedId AND r.reportType = :reportType")
    List<Report> findByReportedIdAndType(@Param("reportedId") String reportedId,
                                        @Param("reportType") Report.ReportType reportType);

    @Query("SELECT COUNT(r) FROM Report r WHERE r.reportedId = :reportedId AND r.reportType = :reportType AND r.status = 'PENDING'")
    long countPendingReportsByReportedIdAndType(@Param("reportedId") String reportedId,
                                               @Param("reportType") Report.ReportType reportType);

    @Query("SELECT r FROM Report r WHERE r.status = 'PENDING' ORDER BY r.createdAt ASC")
    List<Report> findPendingReports();
}
