package ru.lomov.flash.request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lomov.flash.request.entity.Request;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, String> {
    List<Request> findByUserId(String userId);
    List<Request> findByStatus(String status);
    List<Request> findByType(String type);
    List<Request> findByUserIdAndStatus(String userId, String status);
}
