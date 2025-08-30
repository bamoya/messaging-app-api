package com.bamoya.whatsapp_clone.interceptor.user;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@AllArgsConstructor
public class UserSynchronizerFilter extends OncePerRequestFilter {
  private final UserSynchronizer userSynchronizer;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    if (!(SecurityContextHolder.getContext().getAuthentication()
        instanceof AnonymousAuthenticationToken)) {
      JwtAuthenticationToken authenticationToken =
          (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
      userSynchronizer.synchronizeWithIdp(authenticationToken.getToken());
    }
    filterChain.doFilter(request, response);
  }
}
