package com.bamoya.whatsapp_clone.model.notification;

import com.bamoya.whatsapp_clone.model.message.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
public class Notification {
  private String chatId;
  private String content;
  private String chatName;
  private MessageType messageType;
  private NotificationType type;
  private String createdAt;
  private byte[] media;
}
