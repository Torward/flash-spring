package ru.lomov.flash.admin.service;

import ru.lomov.flash.admin.entity.AdminUser;

import java.util.List;
import java.util.Optional;

public interface AdminUserService {
    AdminUser createAdminUser(AdminUser adminUser);
    Optional<AdminUser> getAdminUserById(String id);
    Optional<AdminUser> getAdminUserByUserId(String userId);
    Optional<AdminUser> getAdminUserByEmail(String email);
    Optional<AdminUser> getAdminUserByUsername(String username);
    List<AdminUser> getAllAdminUsers();
    List<AdminUser> getAdminUsersByRole(AdminUser.AdminRole role);
    List<AdminUser> getActiveAdminUsers();
    AdminUser updateAdminUser(String id, AdminUser adminUser);
    void deleteAdminUser(String id);
    boolean isUserAdmin(String userId);
    boolean hasPermission(String userId, String permission);
    void updateLastLogin(String userId);
    void deactivateAdminUser(String id);
    void activateAdminUser(String id);
}
