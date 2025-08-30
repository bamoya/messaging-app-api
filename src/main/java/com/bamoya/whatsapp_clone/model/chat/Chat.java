package com.bamoya.whatsapp_clone.model.chat;

import static jakarta.persistence.GenerationType.UUID;

import com.bamoya.whatsapp_clone.model.common.BaseAuditingEntity;
import com.bamoya.whatsapp_clone.model.message.Message;
import com.bamoya.whatsapp_clone.model.message.MessageState;
import com.bamoya.whatsapp_clone.model.message.MessageType;
import com.bamoya.whatsapp_clone.model.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chat")
@NamedQuery(
    name = ChatConstants.FIND_CHATS_BY_USER_ID,
    query =
        "select distinct c from Chat c where c.sender.id = :userId or c.recipient.id = :userId order by createdAt desc ")
@NamedQuery(
    name = ChatConstants.FIND_CHAT_BY_SENDER_AND_RECIPIENT,
    query =
        "select c from Chat c where (c.sender.id = :senderId and c.recipient.id = :recipientId) or (c.recipient.id = :senderId and c.sender.id = :recipientId)")
public class Chat extends BaseAuditingEntity {

  @Id
  @GeneratedValue(strategy = UUID)
  private String id;

  @ManyToOne
  @JoinColumn(name = "sender_id")
  private User sender;

  @ManyToOne
  @JoinColumn(name = "recipient_id")
  private User recipient;

  @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER)
  @OrderBy("createdAt DESC")
  private List<Message> messages;

  @Transient
  public String getChatName(String senderId) {
    return this.sender.getId().equals(senderId)
        ? this.recipient.getFirstName() + " " + this.recipient.getLastName()
        : this.getSender().getFirstName() + " " + this.sender.getLastName();
  }

  @Transient
  public String getTargetChatName(String senderId) {
    return this.sender.getId().equals(senderId)
        ? this.sender.getFirstName() + " " + this.sender.getLastName()
        : this.recipient.getFirstName() + " " + this.recipient.getLastName();
  }

  @Transient
  public long getUnreadMessages(String recipientId) {
    return this.messages.stream()
        .filter(message -> message.recipientId.equals(recipientId))
        .filter(message -> message.getState().equals(MessageState.SENT))
        .count();
  }

  @Transient
  public String getLastMessage() {
    if (messages != null && !messages.isEmpty()) {
      Message lastMessage = messages.get(0);
      if (!lastMessage.getType().equals(MessageType.TEXT)) {
        return "Attachment";
      }
      return lastMessage.getContent();
    }
    return null;
  }

  @Transient
  public LocalDateTime getLastMessageTime() {
    if (messages != null && !messages.isEmpty()) {
      Message lastMessage = messages.get(0);
      return lastMessage.getCreatedAt();
    }
    return null;
  }
}
