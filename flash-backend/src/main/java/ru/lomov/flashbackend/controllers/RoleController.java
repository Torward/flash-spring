package ru.lomov.flashbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.lomov.flashbackend.entities.AppUser;
import ru.lomov.flashbackend.entities.Role;
import ru.lomov.flashbackend.services.RoleService;
import ru.lomov.flashbackend.services.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;
    private final UserService userService;

    @GetMapping()
    public List<Role> getAllRoles() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable Long id) {
        return roleService.findById(id);
    }

    @PostMapping()
    public Role createRole(@RequestBody Role role) {
        return roleService.save(role);
    }

    @PutMapping("/{id}")
    public Role updateRole(@PathVariable Long id, @RequestBody Role role) {
        role.setId(id);
        return roleService.update(role);
    }

    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        Role role = roleService.findById(id);
        roleService.delete(role);
    }

    @GetMapping("/users/{userId}/roles")
    public List<Role> getRolesByUserId(@PathVariable Long userId) {
        AppUser user = userService.findUserById(userId);
        return roleService.findRolesByUser(user);
    }

    @PostMapping("/users/{userId}/roles")
    public void assignRoleToUser(@PathVariable Long userId, @RequestBody Role role) {
        AppUser user = userService.findUserById(userId);
        roleService.assignRoleToUser(user, role);
    }

    @DeleteMapping("/users/{userId}/roles/{roleId}")
    public void removeRoleFromUser(@PathVariable Long userId, @PathVariable Long roleId) {
        AppUser user = userService.findUserById(userId);
        Role role = roleService.findById(roleId);
        roleService.removeRoleFromUser(user, role);
    }
}
