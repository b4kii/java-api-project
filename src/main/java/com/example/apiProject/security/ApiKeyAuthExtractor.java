package com.example.apiProject.security;

import com.example.apiProject.entity.User;
import com.example.apiProject.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ApiKeyAuthExtractor {

    @Autowired
    private UserService userService;

    public Optional<Authentication> extract(HttpServletRequest request) {
        String providedKey = request.getHeader("X-API-Key");

        if (providedKey == null || !isValidApiKey(providedKey))
            return Optional.empty();

        User user = userService.findUserByApiKey(providedKey);

        if (user == null) {
            return Optional.empty();
        }

        return Optional.of(new ApiKeyAuth(providedKey, AuthorityUtils.NO_AUTHORITIES));
    }

    private boolean isValidApiKey(String apiKey) {
        return apiKey != null && !apiKey.isEmpty();
    }
}
