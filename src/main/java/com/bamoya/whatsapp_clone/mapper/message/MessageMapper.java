package com.bamoya.whatsapp_clone.mapper.message;

import com.bamoya.whatsapp_clone.model.message.Message;
import com.bamoya.whatsapp_clone.service.FileUtils;
import org.springframework.stereotype.Service;

@Service
public class MessageMapper {

  private final FileUtils fileUtils;

  public MessageMapper(FileUtils fileUtils) {
    this.fileUtils = fileUtils;
  }

  public MessageResponse toMessageResponse(Message message) {
    return MessageResponse.builder()
        .id(message.getId())
        .content(message.getContent())
        .senderId(message.getSenderId())
        .receiverId(message.getRecipientId())
        .createdAt(message.getCreatedAt())
        .state(message.getState())
        .type(message.getType())
        .media(fileUtils.readFileFromPath(message.getMediaFilePath()))
        .build();
  }
}
