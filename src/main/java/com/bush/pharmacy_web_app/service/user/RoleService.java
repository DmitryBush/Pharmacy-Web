package com.bush.pharmacy_web_app.service.user;

import com.bush.pharmacy_web_app.model.entity.user.role.Role;
import com.bush.pharmacy_web_app.model.entity.user.role.RoleType;
import com.bush.pharmacy_web_app.repository.user.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoleService {
    private final RoleRepository roleRepository;

    protected Role findRoleByRoleType(RoleType roleType) {
        return roleRepository.findByType(roleType)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }
}
