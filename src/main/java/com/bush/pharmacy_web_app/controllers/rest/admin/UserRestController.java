package com.bush.pharmacy_web_app.controllers.rest.admin;

import com.bush.pharmacy_web_app.model.dto.user.AdminUserReadDto;
import com.bush.pharmacy_web_app.repository.user.UserFilter;
import com.bush.pharmacy_web_app.service.user.UserService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public PagedModel<EntityModel<AdminUserReadDto>> findAllUsers(Pageable pageable,
                                                                  @Validated UserFilter filter,
                                                                  PagedResourcesAssembler<AdminUserReadDto> assembler) {
        return assembler.toModel(userService.findAllByFilter(pageable, filter));
    }

    @PatchMapping(value = "/{mobilePhone}/role", consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AdminUserReadDto> updateUserRole(@PathVariable String mobilePhone,
                                                           @RequestBody @Validated @NotBlank String roleName) {
        return ResponseEntity.ok(userService.updateRole(mobilePhone, roleName));
    }
}
