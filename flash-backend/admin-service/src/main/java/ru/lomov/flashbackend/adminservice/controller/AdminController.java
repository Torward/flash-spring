package ru.lomov.flashbackend.adminservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flashbackend.adminservice.dto.AdminDto;
import ru.lomov.flashbackend.adminservice.dto.ApiResponse;
import ru.lomov.flashbackend.adminservice.dto.CreateAdminDto;
import ru.lomov.flashbackend.adminservice.dto.UpdateAdminDto;
import ru.lomov.flashbackend.adminservice.entity.Admin;
import ru.lomov.flashbackend.adminservice.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<AdminDto> createAdmin(@RequestBody CreateAdminDto createAdminDto) {
        AdminDto adminDto = adminService.createAdmin(createAdminDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(adminDto);
    }

    @GetMapping("/{adminId}")
    public ResponseEntity<AdminDto> getAdmin(@PathVariable String adminId) {
        AdminDto adminDto = adminService.getAdminById(adminId);
        return ResponseEntity.ok(adminDto);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<AdminDto> getAdminByUserId(@PathVariable String userId) {
        AdminDto adminDto = adminService.getAdminByUserId(userId);
        return ResponseEntity.ok(adminDto);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AdminDto> getAdminByEmail(@PathVariable String email) {
        AdminDto adminDto = adminService.getAdminByEmail(email);
        return ResponseEntity.ok(adminDto);
    }

    @GetMapping
    public ResponseEntity<List<AdminDto>> getAllAdmins() {
        List<AdminDto> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<AdminDto>> getAdminsByRole(@PathVariable Admin.AdminRole role) {
        List<AdminDto> admins = adminService.getAdminsByRole(role);
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AdminDto>> getAdminsByStatus(@PathVariable Admin.AdminStatus status) {
        List<AdminDto> admins = adminService.getAdminsByStatus(status);
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/role/{role}/status/{status}")
    public ResponseEntity<List<AdminDto>> getAdminsByRoleAndStatus(
            @PathVariable Admin.AdminRole role,
            @PathVariable Admin.AdminStatus status) {
        List<AdminDto> admins = adminService.getAdminsByRoleAndStatus(role, status);
        return ResponseEntity.ok(admins);
    }

    @PutMapping("/{adminId}")
    public ResponseEntity<AdminDto> updateAdmin(
            @PathVariable String adminId,
            @RequestBody UpdateAdminDto updateAdminDto) {
        AdminDto updatedAdmin = adminService.updateAdmin(adminId, updateAdminDto);
        return ResponseEntity.ok(updatedAdmin);
    }

    @DeleteMapping("/{adminId}")
    public ResponseEntity<ApiResponse> deleteAdmin(@PathVariable String adminId) {
        adminService.deleteAdmin(adminId);
        return ResponseEntity.ok(new ApiResponse());
    }

    @PostMapping("/{adminId}/suspend")
    public ResponseEntity<ApiResponse> suspendAdmin(@PathVariable String adminId) {
        adminService.suspendAdmin(adminId);
        return ResponseEntity.ok(new ApiResponse());
    }

    @PostMapping("/{adminId}/activate")
    public ResponseEntity<ApiResponse> activateAdmin(@PathVariable String adminId) {
        adminService.activateAdmin(adminId);
        return ResponseEntity.ok(new ApiResponse());
    }

    @GetMapping("/exists/user/{userId}")
    public ResponseEntity<Boolean> existsByUserId(@PathVariable String userId) {
        boolean exists = adminService.existsByUserId(userId);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        boolean exists = adminService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/count/active")
    public ResponseEntity<Long> getActiveAdminCount() {
        long count = adminService.getActiveAdminCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/permission/{permission}")
    public ResponseEntity<List<AdminDto>> getAdminsByPermission(@PathVariable String permission) {
        List<AdminDto> admins = adminService.getAdminsByPermission(permission);
        return ResponseEntity.ok(admins);
    }

    @GetMapping("/{adminId}/permission/{permission}")
    public ResponseEntity<Boolean> hasPermission(
            @PathVariable String adminId,
            @PathVariable Admin.AdminPermission permission) {
        boolean hasPermission = adminService.hasPermission(adminId, permission);
        return ResponseEntity.ok(hasPermission);
    }

    @PutMapping("/{adminId}/permissions")
    public ResponseEntity<ApiResponse> updateAdminPermissions(
            @PathVariable String adminId,
            @RequestBody List<Admin.AdminPermission> permissions) {
        adminService.updateAdminPermissions(adminId, permissions);
        return ResponseEntity.ok(new ApiResponse());
    }
}
