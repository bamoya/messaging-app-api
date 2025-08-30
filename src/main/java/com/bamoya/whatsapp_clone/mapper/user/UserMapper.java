package com.bamoya.whatsapp_clone.mapper.user;

import com.bamoya.whatsapp_clone.model.user.User;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
  public User fromToken(Map<String, Object> claims) {
    User user = new User();

    if (claims.containsKey("sub")) {
      user.setId(claims.get("sub").toString());
    }

    if (claims.containsKey("given_name")) {
      user.setFirstName(claims.get("given_name").toString());
    } else if (claims.containsKey("nickname")) {
      user.setFirstName(claims.get("nickname").toString());
    }

    if (claims.containsKey("family_name")) {
      user.setLastName(claims.get("family_name").toString());
    }
    if (claims.containsKey("email")) {
      user.setEmail(claims.get("email").toString());
    }
    user.setLastSeen(LocalDateTime.now());
    return user;
  }

  public UserResponse toUserResponse(User user) {
    return UserResponse.builder()
        .id(user.getId())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .email(user.getEmail())
        .isOnline(user.isUserOnline())
        .lastSeen(user.getLastSeen())
        .build();
  }
}
