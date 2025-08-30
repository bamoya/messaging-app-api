package com.bamoya.whatsapp_clone.service;

import com.bamoya.whatsapp_clone.mapper.chat.ChatMapper;
import com.bamoya.whatsapp_clone.mapper.chat.ChatResponse;
import com.bamoya.whatsapp_clone.model.chat.Chat;
import com.bamoya.whatsapp_clone.model.user.User;
import com.bamoya.whatsapp_clone.repository.ChatRepository;
import com.bamoya.whatsapp_clone.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {
  private final ChatRepository chatRepository;
  private final ChatMapper chatMapper;
  private final UserRepository userRepository;

  public String createChat(String senderId, String recipientId) {
    Optional<Chat> existingChat =
        chatRepository.findChatsBySenderAndRecipient(senderId, recipientId);
    if (existingChat.isPresent()) {
      return existingChat.get().getId();
    }

    User sender =
        userRepository
            .findUserByPublicId(senderId)
            .orElseThrow(
                () -> new EntityNotFoundException("Sender With Email " + senderId + " Not Found"));
    User recipient =
        userRepository
            .findUserByPublicId(recipientId)
            .orElseThrow(
                () -> new EntityNotFoundException("Sender With Email " + senderId + " Not Found"));

    Chat chat = new Chat();
    chat.setSender(sender);
    chat.setRecipient(recipient);
    Chat chatEntity = chatRepository.save(chat);

    return chatEntity.getId();
  }

  @Transactional(readOnly = true)
  public List<ChatResponse> getUserChats(Authentication currentUser) {
    final String userId = currentUser.getName();
    List<Chat> userChats = chatRepository.findChatsByUserId(userId);
    return userChats.stream().map(chat -> chatMapper.toChatResponse(chat, userId)).toList();
  }
}
