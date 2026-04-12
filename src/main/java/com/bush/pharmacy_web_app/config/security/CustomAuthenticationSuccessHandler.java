package com.bush.pharmacy_web_app.config.security;

import com.bush.pharmacy_web_app.validation.uri.UriValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final UriValidator uriValidator;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            response.sendError(HttpStatus.NO_CONTENT.value());
        } else {
            HttpSession session = request.getSession();
            String targetUri = Optional.ofNullable(session.getAttribute("originLoginUri"))
                    .map(attribute -> (String) attribute)
                    .orElse("/");
            if (uriValidator.isValidRelativePath(targetUri)) {
                response.sendRedirect(targetUri);
            } else {
                response.sendRedirect("/");
            }
        }
    }
}
