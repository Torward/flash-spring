package ru.lomov.flashbackend.adminservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lomov.flashbackend.adminservice.entity.Admin;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

    Optional<Admin> findByUserId(String userId);

    Optional<Admin> findByEmail(String email);

    List<Admin> findByRole(Admin.AdminRole role);

    List<Admin> findByStatus(Admin.AdminStatus status);

    @Query("SELECT a FROM Admin a WHERE a.role = :role AND a.status = :status")
    List<Admin> findByRoleAndStatus(@Param("role") Admin.AdminRole role, @Param("status") Admin.AdminStatus status);

    boolean existsByUserId(String userId);

    boolean existsByEmail(String email);

    @Query("SELECT COUNT(a) FROM Admin a WHERE a.status = 'ACTIVE'")
    long countActiveAdmins();

    @Query("SELECT a FROM Admin a WHERE a.permissions LIKE %:permission%")
    List<Admin> findByPermission(@Param("permission") String permission);
}
