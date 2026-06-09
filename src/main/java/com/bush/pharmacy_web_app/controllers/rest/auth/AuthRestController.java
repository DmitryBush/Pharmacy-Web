package com.bush.pharmacy_web_app.controllers.rest.auth;

import com.bush.pharmacy_web_app.model.dto.user.CustomerCreateDto;
import com.bush.pharmacy_web_app.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final UserService userService;

    @GetMapping("/me/status")
    public ResponseEntity<Void> getAuthStatus(@AuthenticationPrincipal UserDetails userDetails) {
        if (Objects.isNull(userDetails)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Validated @RequestBody CustomerCreateDto dto) {
        userService.create(dto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
