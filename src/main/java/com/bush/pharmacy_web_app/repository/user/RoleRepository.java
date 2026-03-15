package com.bush.pharmacy_web_app.repository.user;

import com.bush.pharmacy_web_app.model.entity.user.role.Role;
import com.bush.pharmacy_web_app.model.entity.user.role.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByType(RoleType type);
}
