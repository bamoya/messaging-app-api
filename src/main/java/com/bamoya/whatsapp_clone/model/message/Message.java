package com.bamoya.whatsapp_clone.model.message;

import static jakarta.persistence.GenerationType.SEQUENCE;

import com.bamoya.whatsapp_clone.model.chat.Chat;
import com.bamoya.whatsapp_clone.model.common.BaseAuditingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "message")
@NamedQuery(
    name = MessageConstants.FIND_MESSAGE_BY_CHAT_ID,
    query = "select m from Message m where m.chat.id = :chatId order by createdAt")
@NamedQuery(
    name = MessageConstants.SET_MESSAGE_TO_SEEN_BY_USER,
    query = "update Message set state = 'SEEN' where chat.id = :chatId and recipientId = :userId")
public class Message extends BaseAuditingEntity {
  @Id
  @SequenceGenerator(name = "msg_seq", sequenceName = "msg_seq", allocationSize = 1)
  @GeneratedValue(strategy = SEQUENCE, generator = "msg_seq")
  private Integer id;

  @Column(columnDefinition = "TEXT")
  private String content;

  @Enumerated(EnumType.STRING)
  private MessageState state;

  @Enumerated(EnumType.STRING)
  private MessageType type;

  private String mediaFilePath;

  @Column(name = "recipient_id", nullable = false)
  public String recipientId;

  @Column(name = "sender_id", nullable = false)
  public String senderId;

  @ManyToOne
  @JoinColumn(name = "chat_id")
  private Chat chat;
}
