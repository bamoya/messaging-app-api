package com.bamoya.whatsapp_clone.repository;

import com.bamoya.whatsapp_clone.model.chat.Chat;
import com.bamoya.whatsapp_clone.model.chat.ChatConstants;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, String> {
  @Query(name = ChatConstants.FIND_CHATS_BY_USER_ID)
  List<Chat> findChatsByUserId(String userId);

  @Query(name = ChatConstants.FIND_CHAT_BY_SENDER_AND_RECIPIENT)
  Optional<Chat> findChatsBySenderAndRecipient(String senderId, String recipientId);
}
