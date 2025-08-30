package com.bamoya.whatsapp_clone.controller;

import com.bamoya.whatsapp_clone.mapper.StringResponse;
import com.bamoya.whatsapp_clone.mapper.chat.ChatResponse;
import com.bamoya.whatsapp_clone.service.ChatService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
@Tag(name = "Chat")
public class ChatController {
  private final ChatService chatService;

  @PostMapping
  public ResponseEntity<StringResponse> createChat(
      @RequestParam(name = "sender-id") String senderId,
      @RequestParam(name = "recipient-id") String recipientId) {
    final String chatId = chatService.createChat(senderId, recipientId);
    return ResponseEntity.ok(StringResponse.builder().response(chatId).build());
  }

  @GetMapping
  public ResponseEntity<List<ChatResponse>> getUserChats(Authentication currentUser) {
    return ResponseEntity.ok(chatService.getUserChats(currentUser));
  }
}
