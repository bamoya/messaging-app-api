package com.bamoya.whatsapp_clone.repository;

import com.bamoya.whatsapp_clone.model.message.Message;
import com.bamoya.whatsapp_clone.model.message.MessageConstants;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {
  @Query(name = MessageConstants.FIND_MESSAGE_BY_CHAT_ID)
  public List<Message> findMessageByChatId(String chatId);

  @Query(name = MessageConstants.SET_MESSAGE_TO_SEEN_BY_USER)
  @Modifying
  public void setMessageToSeenByUser(String chatId, String userId);
}
