package com.bamoya.whatsapp_clone.interceptor.user;

import com.bamoya.whatsapp_clone.mapper.user.UserMapper;
import com.bamoya.whatsapp_clone.model.user.User;
import com.bamoya.whatsapp_clone.repository.UserRepository;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserSynchronizer {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public void synchronizeWithIdp(Jwt token) {
    log.info("Synchronizing user with idp");
    getUserEmail(token)
        .ifPresent(
            email -> {
              log.info("Synchronizing user with email {}", email);
              Optional<User> dbUser = userRepository.findByEmail(email);
              User idpUser = userMapper.fromToken(token.getClaims());
              // if the userId changed delete the user and create new one ( avoid duplications )
              dbUser.ifPresent(
                  user -> {
                    if (!user.getId().equals(idpUser.getId())) {
                      userRepository.delete(user);
                    }
                  });
              userRepository.save(idpUser);
            });
  }

  private Optional<String> getUserEmail(Jwt token) {
    Map<String, Object> claims = token.getClaims();
    if (claims.containsKey("email")) {
      return Optional.of(claims.get("email").toString());
    }
    return Optional.empty();
  }
}
