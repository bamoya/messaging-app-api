package com.bamoya.whatsapp_clone.mapper.chat;

import com.bamoya.whatsapp_clone.model.chat.Chat;
import org.springframework.stereotype.Service;

@Service
public class ChatMapper {
  public ChatResponse toChatResponse(Chat chat, String senderId) {
    return ChatResponse.builder()
        .id(chat.getId())
        .name(chat.getChatName(senderId))
        .unreadCount(chat.getUnreadMessages(senderId))
        .lastMessage(chat.getLastMessage())
        .lastMessageTime(chat.getLastMessageTime())
        .isRecipientOnline(chat.getRecipient().isUserOnline())
        .senderId(chat.getSender().getId())
        .receiverId(chat.getRecipient().getId())
        .build();
  }
}
