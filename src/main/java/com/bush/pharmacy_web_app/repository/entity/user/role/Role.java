package com.bush.pharmacy_web_app.repository.entity.user.role;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false)
    private Integer id;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role_name", nullable = false, unique = true)
    private RoleType type;
}
