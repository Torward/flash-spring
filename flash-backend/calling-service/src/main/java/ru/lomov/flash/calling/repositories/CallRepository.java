package ru.lomov.flash.calling.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lomov.flash.calling.entities.Call;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CallRepository extends JpaRepository<Call, String> {

    // Find all calls for a specific user (as caller or receiver)
    @Query("SELECT c FROM Call c WHERE c.callerId = :userId OR c.receiverId = :userId ORDER BY c.createdAt DESC")
    List<Call> findByUserId(@Param("userId") String userId);

    // Find active calls for a user
    @Query("SELECT c FROM Call c WHERE (c.callerId = :userId OR c.receiverId = :userId) AND c.status IN ('INITIATED', 'RINGING', 'CONNECTED')")
    List<Call> findActiveCallsByUserId(@Param("userId") String userId);

    // Find calls between two users
    @Query("SELECT c FROM Call c WHERE (c.callerId = :userId1 AND c.receiverId = :userId2) OR (c.callerId = :userId2 AND c.receiverId = :userId1) ORDER BY c.createdAt DESC")
    List<Call> findCallsBetweenUsers(@Param("userId1") String userId1, @Param("userId2") String userId2);

    // Find calls by status
    List<Call> findByStatus(Call.CallStatus status);

    // Find calls by type
    List<Call> findByType(Call.CallType type);

    // Find calls within date range
    @Query("SELECT c FROM Call c WHERE c.createdAt BETWEEN :startDate AND :endDate ORDER BY c.createdAt DESC")
    List<Call> findByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Find calls by room ID
    Optional<Call> findByRoomId(String roomId);

    // Count total calls for a user
    @Query("SELECT COUNT(c) FROM Call c WHERE c.callerId = :userId OR c.receiverId = :userId")
    Long countByUserId(@Param("userId") String userId);

    // Count missed calls for a user
    @Query("SELECT COUNT(c) FROM Call c WHERE c.receiverId = :userId AND c.status = 'MISSED'")
    Long countMissedCallsByUserId(@Param("userId") String userId);

    // Find recent calls for a user (last 30 days)
    @Query("SELECT c FROM Call c WHERE (c.callerId = :userId OR c.receiverId = :userId) AND c.createdAt >= :since ORDER BY c.createdAt DESC")
    List<Call> findRecentCallsByUserId(@Param("userId") String userId, @Param("since") LocalDateTime since);
}
