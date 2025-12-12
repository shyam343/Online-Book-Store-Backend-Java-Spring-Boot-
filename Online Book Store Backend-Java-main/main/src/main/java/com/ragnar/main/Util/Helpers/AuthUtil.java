package com.ragnar.main.Util.Helpers;

import com.ragnar.main.Application.IServices.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
@RequiredArgsConstructor
public class AuthUtil {
    private final IUserService userService;

    public Long getUserIdFromPrincipal(Principal principal) {
        String username = principal.getName();
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Invalid username in principal");
        }

        var userResponse = userService.GetUserByUsername(username);
        if (userResponse == null || userResponse.getData() == null) {
            throw new RuntimeException("User not found for username: " + username);
        }

        return userResponse.getData().getUserId();
    }
}
