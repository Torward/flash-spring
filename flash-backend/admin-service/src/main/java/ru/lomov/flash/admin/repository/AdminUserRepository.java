package ru.lomov.flash.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lomov.flash.admin.entity.AdminUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, String> {
    Optional<AdminUser> findByUserId(String userId);
    Optional<AdminUser> findByEmail(String email);
    Optional<AdminUser> findByUsername(String username);
    List<AdminUser> findByRole(AdminUser.AdminRole role);
    List<AdminUser> findByIsActiveTrue();
    boolean existsByUserId(String userId);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
