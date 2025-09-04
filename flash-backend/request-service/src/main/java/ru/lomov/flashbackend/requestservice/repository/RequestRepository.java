package ru.lomov.flashbackend.requestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lomov.flashbackend.requestservice.entity.Request;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, String> {
    List<Request> findByUserId(String userId);
    List<Request> findByStatus(String status);
}
