package ru.lomov.flash.admin.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flash.admin.entity.AdminUser;
import ru.lomov.flash.admin.service.AdminUserService;

import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    @PostMapping
    public ResponseEntity<AdminUser> createAdminUser(@RequestBody AdminUser adminUser) {
        AdminUser createdUser = adminUserService.createAdminUser(adminUser);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminUser> getAdminUserById(@PathVariable String id) {
        return adminUserService.getAdminUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<AdminUser> getAdminUserByUserId(@PathVariable String userId) {
        return adminUserService.getAdminUserByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AdminUser> getAdminUserByEmail(@PathVariable String email) {
        return adminUserService.getAdminUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<AdminUser> getAdminUserByUsername(@PathVariable String username) {
        return adminUserService.getAdminUserByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<AdminUser>> getAllAdminUsers() {
        List<AdminUser> users = adminUserService.getAllAdminUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<AdminUser>> getAdminUsersByRole(@PathVariable AdminUser.AdminRole role) {
        List<AdminUser> users = adminUserService.getAdminUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/active")
    public ResponseEntity<List<AdminUser>> getActiveAdminUsers() {
        List<AdminUser> users = adminUserService.getActiveAdminUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdminUser> updateAdminUser(@PathVariable String id, @RequestBody AdminUser adminUser) {
        try {
            AdminUser updatedUser = adminUserService.updateAdminUser(id, adminUser);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdminUser(@PathVariable String id) {
        try {
            adminUserService.deleteAdminUser(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/check/{userId}")
    public ResponseEntity<Boolean> isUserAdmin(@PathVariable String userId) {
        boolean isAdmin = adminUserService.isUserAdmin(userId);
        return ResponseEntity.ok(isAdmin);
    }

    @GetMapping("/permission/{userId}")
    public ResponseEntity<Boolean> hasPermission(@PathVariable String userId, @RequestParam String permission) {
        boolean hasPermission = adminUserService.hasPermission(userId, permission);
        return ResponseEntity.ok(hasPermission);
    }

    @PostMapping("/login/{userId}")
    public ResponseEntity<Void> updateLastLogin(@PathVariable String userId) {
        adminUserService.updateLastLogin(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deactivate/{id}")
    public ResponseEntity<Void> deactivateAdminUser(@PathVariable String id) {
        adminUserService.deactivateAdminUser(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/activate/{id}")
    public ResponseEntity<Void> activateAdminUser(@PathVariable String id) {
        adminUserService.activateAdminUser(id);
        return ResponseEntity.ok().build();
    }
}
