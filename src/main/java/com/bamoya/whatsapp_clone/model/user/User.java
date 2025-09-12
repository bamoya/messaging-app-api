package com.bamoya.whatsapp_clone.model.user;

import static java.time.LocalDateTime.now;

import com.bamoya.whatsapp_clone.model.chat.Chat;
import com.bamoya.whatsapp_clone.model.common.BaseAuditingEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
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
@Table(name = "users")
@NamedQuery(
    name = UserConstants.FIND_USER_BY_EMAIL,
    query = "SELECT u from User u WHERE u.email=:email")
@NamedQuery(
    name = UserConstants.FIND_ALL_USERS_EXCEPT_SELF,
    query = "SELECT u from User u where u.id != :publicId")
@NamedQuery(
    name = UserConstants.FIND_USER_BY_PUBLIC_ID,
    query = "select u from User u where u.id = :publicId")
public class User extends BaseAuditingEntity {

  private static final int LAST_ACTIVATE_INTERVAL = 5;
  @Id private String id;
  private String firstName;
  private String lastName;
  private String email;
  private LocalDateTime lastSeen;

  @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Chat> chatsAsSender;

  @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Chat> chatsAsRecipient;

  @Transient
  public boolean isUserOnline() {
    return lastSeen != null && lastSeen.isBefore(now().plusSeconds(LAST_ACTIVATE_INTERVAL));
  }
}
