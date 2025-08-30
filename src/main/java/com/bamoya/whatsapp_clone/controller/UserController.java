package com.bamoya.whatsapp_clone.controller;

import com.bamoya.whatsapp_clone.mapper.user.UserResponse;
import com.bamoya.whatsapp_clone.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping
  public ResponseEntity<List<UserResponse>> getAllUsersExceptSelf(Authentication authentication) {
    return ResponseEntity.ok(userService.getAllUsersExceptSelf(authentication));
  }
}
