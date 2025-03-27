package com.bush.pharmacy_web_app.repository.entity.role;

import org.springframework.security.core.GrantedAuthority;

public enum RoleType implements GrantedAuthority {
    ADMIN, OPERATOR, CUSTOMER;

    @Override
    public String getAuthority() {
        return name();
    }
}
