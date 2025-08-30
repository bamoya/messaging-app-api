package com.bamoya.whatsapp_clone.service;

import com.bamoya.whatsapp_clone.mapper.message.MessageMapper;
import com.bamoya.whatsapp_clone.mapper.message.MessageRequest;
import com.bamoya.whatsapp_clone.mapper.message.MessageResponse;
import com.bamoya.whatsapp_clone.model.chat.Chat;
import com.bamoya.whatsapp_clone.model.message.Message;
import com.bamoya.whatsapp_clone.model.message.MessageState;
import com.bamoya.whatsapp_clone.model.message.MessageType;
import com.bamoya.whatsapp_clone.model.notification.Notification;
import com.bamoya.whatsapp_clone.model.notification.NotificationType;
import com.bamoya.whatsapp_clone.repository.ChatRepository;
import com.bamoya.whatsapp_clone.repository.MessageRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class MessageService {
  private final MessageRepository messageRepository;
  private final ChatRepository chatRepository;
  private final MessageMapper messageMapper;
  private final FileService fileService;
  private final NotificationService notificationService;
  private final FileUtils fileUtils;

  public List<MessageResponse> getMessagesByChatId(String chatId) {
    return messageRepository.findMessageByChatId(chatId).stream()
        .map(messageMapper::toMessageResponse)
        .toList();
  }

  public void sendMessage(MessageRequest messageRequest, Authentication auth) {
    Chat chat =
        chatRepository
            .findById(messageRequest.getChatId())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Chat with Id %s NOT FOUND".formatted(messageRequest.getChatId())));
    final String recipientId = getRecipientId(chat, auth);
    final String senderId = auth.getName();
    Message message = new Message();
    message.setChat(chat);
    message.setRecipientId(recipientId);
    message.setSenderId(senderId);
    message.setContent(messageRequest.getContent());
    message.setType(messageRequest.getType());
    message.setState(MessageState.SENT);

    messageRepository.save(message);

    // send notification
    Notification notification =
        Notification.builder()
            .chatId(chat.getId())
            .chatName(chat.getChatName(recipientId))
            .type(NotificationType.MESSAGE)
            .messageType(MessageType.TEXT)
            .content(message.getContent())
            .createdAt(message.getCreatedAt().toString())
            .build();

    notificationService.sendNotification(recipientId, notification);
  }

  @Transactional
  public void setMessagesToSeen(String chatId, Authentication auth) {
    Chat chat =
        chatRepository
            .findById(chatId)
            .orElseThrow(
                () -> new EntityNotFoundException("Chat with Id %s NOT FOUND".formatted(chatId)));
    //    final String senderId = getRecipientId(chat, auth);
    messageRepository.setMessageToSeenByUser(chatId, auth.getName());

    String recipientId = getRecipientId(chat, auth);

    // send notification
    Notification notification =
        Notification.builder()
            .type(NotificationType.SEEN)
            .chatId(chat.getId())
            .chatName(chat.getChatName(recipientId))
            .build();

    notificationService.sendNotification(recipientId, notification);
  }

  public void uploadMediaMessage(String chatId, MultipartFile file, Authentication auth) {
    Chat chat =
        chatRepository
            .findById(chatId)
            .orElseThrow(
                () -> new EntityNotFoundException("Chat with Id %s NOT FOUND".formatted(chatId)));
    final String senderId = auth.getName();
    final String recipientId = getRecipientId(chat, auth);
    final String filePath = fileService.getPath(file, senderId);

    Message mediaMessage = new Message();
    mediaMessage.setChat(chat);
    mediaMessage.setSenderId(senderId);
    mediaMessage.setRecipientId(recipientId);
    mediaMessage.setMediaFilePath(filePath);
    mediaMessage.setState(MessageState.SENT);

    // to-do add specific type for each file voice image ...
    mediaMessage.setType(MessageType.IMAGE);

    messageRepository.save(mediaMessage);
    // send notification
    Notification notification =
        Notification.builder()
            .type(NotificationType.MESSAGE)
            .messageType(MessageType.IMAGE)
            .chatId(chat.getId())
            .chatName(chat.getChatName(recipientId))
            .media(fileUtils.readFileFromPath(mediaMessage.getMediaFilePath()))
            .createdAt(mediaMessage.getCreatedAt().toString())
            .build();

    notificationService.sendNotification(recipientId, notification);
  }

  private String getRecipientId(Chat chat, Authentication auth) {
    if (chat.getSender().getId().equals(auth.getName())) {
      return chat.getRecipient().getId();
    }
    return chat.getSender().getId();
  }

  //  private String getSenderId(Authentication auth) {
  //    return auth.getName();
  //  }
}
