package com.bamoya.whatsapp_clone.mapper.message;

import com.bamoya.whatsapp_clone.model.message.MessageState;
import com.bamoya.whatsapp_clone.model.message.MessageType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MessageResponse {
  private Integer id;
  private String content;
  private MessageType type;
  private MessageState state;
  private String senderId;
  private String receiverId;
  private LocalDateTime createdAt;
  private byte[] media;
}
