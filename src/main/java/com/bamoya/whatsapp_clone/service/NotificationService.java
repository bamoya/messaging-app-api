package com.bamoya.whatsapp_clone.service;

import com.bamoya.whatsapp_clone.model.notification.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
  private final SimpMessagingTemplate messagingTemplate;

  public void sendNotification(String userId, Notification notification) {
    messagingTemplate.convertAndSendToUser(userId, "/chat", notification);
  }
}
