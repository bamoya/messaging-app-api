package com.bamoya.whatsapp_clone.mapper.message;

import com.bamoya.whatsapp_clone.model.message.MessageType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequest {
  private String chatId;
  private String content;
  private MessageType type;
}
