package ru.lomov.flashbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lomov.flashbackend.entities.VerificationRequest;

import java.util.List;

@Repository
public interface VerificationRequestRepository extends JpaRepository<VerificationRequest, String> {
    List<VerificationRequest> findByUserId(String userId);
    List<VerificationRequest> findByStatus(String status);
}
