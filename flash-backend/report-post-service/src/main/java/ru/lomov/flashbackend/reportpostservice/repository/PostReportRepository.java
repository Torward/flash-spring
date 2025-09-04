package ru.lomov.flashbackend.reportpostservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lomov.flashbackend.reportpostservice.entity.PostReport;

import java.util.List;

@Repository
public interface PostReportRepository extends JpaRepository<PostReport, String> {

    List<PostReport> findByPostId(String postId);

    List<PostReport> findByReportedByUserId(String reportedByUserId);

    List<PostReport> findByResolved(boolean resolved);

    List<PostReport> findByPostIdAndResolved(String postId, boolean resolved);

    @Query("SELECT COUNT(r) FROM PostReport r WHERE r.postId = :postId")
    long countByPostId(@Param("postId") String postId);

    @Query("SELECT COUNT(r) FROM PostReport r WHERE r.resolved = false")
    long countUnresolvedReports();

    @Query("SELECT r FROM PostReport r WHERE r.resolved = false ORDER BY r.createdAt ASC")
    List<PostReport> findUnresolvedReportsOrderByCreatedAtAsc();

    boolean existsByPostIdAndReportedByUserId(String postId, String reportedByUserId);
}
