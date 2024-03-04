package com.food_app_api.Viviepi.config;

import com.food_app_api.Viviepi.entities.token.Tokens;
import com.food_app_api.Viviepi.repositories.ITokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class LogoutConfig implements LogoutHandler {

    private final ITokenRepository tokenRepository;

    @Autowired
    public LogoutConfig(final ITokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication
    ) {
        final String headerValue = request.getHeader("Authorization");
        if (headerValue != null && headerValue.startsWith("Bearer ")) {
            final String token = headerValue.substring(7);
            Tokens storedTokens = tokenRepository.findByToken(token).orElse(null);
            if (storedTokens != null){
                storedTokens.setExpired(true);
                storedTokens.setRevoke(true);
                this.tokenRepository.save(storedTokens);
            }
        }
    }


}
