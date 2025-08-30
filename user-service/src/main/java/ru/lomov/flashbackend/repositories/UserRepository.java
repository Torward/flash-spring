package ru.lomov.flashbackend.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.lomov.flashbackend.entities.AppUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
    
    // Search users by first name, last name, or email
    Page<AppUser> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String firstName, String lastName, String email, Pageable pageable);
    
    // Get followers of a user (users who follow the given user)
    @Query("SELECT u FROM AppUser u JOIN u.following f WHERE f.userId = :userId")
    Page<AppUser> findFollowersByUserId(@Param("userId") Long userId, Pageable pageable);
    
    // Get users that a user is following (users followed by the given user)
    @Query("SELECT u FROM AppUser u JOIN u.followers f WHERE f.userId = :userId")
    Page<AppUser> findFollowingByUserId(@Param("userId") Long userId, Pageable pageable);
}
