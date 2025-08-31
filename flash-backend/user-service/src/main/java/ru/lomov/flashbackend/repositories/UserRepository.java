package ru.lomov.flashbackend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.lomov.flashbackend.entities.AppUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, String> {
    Optional<AppUser> findByEmail(String email);
    
    // Search users by name or email (Firebase compatible)
    Page<AppUser> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String name, String email, Pageable pageable);
    
    // Get followers of a user (users who follow the given user)
    @Query("SELECT u FROM AppUser u WHERE u IN (SELECT f FROM AppUser us JOIN us.followers f WHERE us.userId = :userId)")
    Page<AppUser> findFollowersByUserId(@Param("userId") String userId, Pageable pageable);
    
    // Get users that a user is following (users followed by the given user)
    @Query("SELECT u FROM AppUser u WHERE u IN (SELECT f FROM AppUser us JOIN us.following f WHERE us.userId = :userId)")
    Page<AppUser> findFollowingByUserId(@Param("userId") String userId, Pageable pageable);
}
