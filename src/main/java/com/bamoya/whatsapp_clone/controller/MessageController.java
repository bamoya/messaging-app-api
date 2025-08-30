package com.bamoya.whatsapp_clone.controller;

import com.bamoya.whatsapp_clone.mapper.message.MessageRequest;
import com.bamoya.whatsapp_clone.mapper.message.MessageResponse;
import com.bamoya.whatsapp_clone.service.MessageService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Message")
@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
  private final MessageService messageService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void sendMessage(@RequestBody MessageRequest messageRequest, Authentication auth) {
    messageService.sendMessage(messageRequest, auth);
  }

  @PostMapping(value = "/upload-media", consumes = "multipart/form-data")
  @ResponseStatus(HttpStatus.CREATED)
  public void uploadFile(
      @RequestParam("chat-id") String chatId,
      @Parameter @RequestParam("file") MultipartFile file,
      Authentication auth) {
    messageService.uploadMediaMessage(chatId, file, auth);
  }

  @PatchMapping
  @ResponseStatus(HttpStatus.ACCEPTED)
  public void updateMessageStatusToSeen(
      @RequestParam("chat-id") String chatId, Authentication auth) {
    messageService.setMessagesToSeen(chatId, auth);
  }

  @GetMapping(value = "/chat/{chat-id}")
  public ResponseEntity<List<MessageResponse>> getChatMessages(
      @PathVariable("chat-id") String chatId) {
    return ResponseEntity.ok(messageService.getMessagesByChatId(chatId));
  }
}
