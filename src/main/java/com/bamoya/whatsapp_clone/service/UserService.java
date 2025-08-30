package com.bamoya.whatsapp_clone.service;

import com.bamoya.whatsapp_clone.mapper.user.UserMapper;
import com.bamoya.whatsapp_clone.mapper.user.UserResponse;
import com.bamoya.whatsapp_clone.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public List<UserResponse> getAllUsersExceptSelf(Authentication auth) {
    return userRepository.findAllUsersExceptSelf(auth.getName()).stream()
        .map(userMapper::toUserResponse)
        .toList();
  }
}
