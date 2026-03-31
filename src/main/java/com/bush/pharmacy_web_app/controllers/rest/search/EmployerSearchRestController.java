package com.bush.pharmacy_web_app.controllers.rest.search;

import com.bush.pharmacy_web_app.model.dto.user.AdminUserReadDto;
import com.bush.pharmacy_web_app.repository.user.UserFilter;
import com.bush.pharmacy_web_app.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/search/employees")
@RequiredArgsConstructor
public class EmployerSearchRestController {
    private final UserService userService;

    @GetMapping("/filter")
    public ResponseEntity<Page<AdminUserReadDto>> findEmployeesByMobilePhone(@PageableDefault(5) Pageable pageable,
                                                                             @Validated UserFilter userFilter) {
        return ResponseEntity.ok(userService.findAllByFilter(pageable, userFilter));
    }
}
