package ru.lomov.flash.chat.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.lomov.flash.chat.entities.ChatMessage;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID> {

    List<ChatMessage> findBySenderIdAndReceiverIdOrderByCreatedAtDesc(String senderId, String receiverId);

    List<ChatMessage> findByGroupIdOrderByCreatedAtDesc(String groupId);

    List<ChatMessage> findByPartyIdOrderByCreatedAtDesc(String partyId);

    @Query("SELECT m FROM ChatMessage m WHERE (m.senderId = :userId OR m.receiverId = :userId) AND m.groupId IS NULL AND m.partyId IS NULL ORDER BY m.createdAt DESC")
    List<ChatMessage> findPrivateMessagesByUserId(@Param("userId") String userId);

    @Query("SELECT m FROM ChatMessage m WHERE m.groupId = :groupId AND m.createdAt > :since ORDER BY m.createdAt ASC")
    List<ChatMessage> findMessagesByGroupIdSince(@Param("groupId") String groupId, @Param("since") java.time.LocalDateTime since);

    @Query("SELECT m FROM ChatMessage m WHERE m.partyId = :partyId AND m.createdAt > :since ORDER BY m.createdAt ASC")
    List<ChatMessage> findMessagesByPartyIdSince(@Param("partyId") String partyId, @Param("since") java.time.LocalDateTime since);
}
