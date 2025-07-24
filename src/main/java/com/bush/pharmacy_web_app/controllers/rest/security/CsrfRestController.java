package com.bush.pharmacy_web_app.controllers.rest.security;

import com.bush.pharmacy_web_app.repository.dto.security.CsrfDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/csrf")
public class CsrfRestController {

    @GetMapping
    public ResponseEntity<CsrfDto> getCSRFToken(CsrfToken token) {
        return ResponseEntity.ok(new CsrfDto(token.getHeaderName(), token.getToken()));
    }
}
