package com.bush.pharmacy_web_app.repository.entity.user;

import com.bush.pharmacy_web_app.repository.entity.branch.BranchUserAssignment;
import com.bush.pharmacy_web_app.repository.entity.branch.PharmacyBranch;
import com.bush.pharmacy_web_app.repository.entity.order.Order;
import com.bush.pharmacy_web_app.repository.entity.user.role.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"orders"})
@Builder
@Entity
@Table(name = "customers")
public class User {
    @Id
    @Column(name = "mobile_phone", nullable = false, length = 15)
    private String mobilePhone;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Column(name = "last_name")
    private String lastName;
    @Column(nullable = false)
    private String password;
    @Builder.Default
    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "id_user", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "id_role", nullable = false))
    private Set<Role> roles = new HashSet<>();
    @Builder.Default
    @ManyToMany
    @JoinTable(name = "branch_user_assignment",
            joinColumns = @JoinColumn(name = "user_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "branch_id", nullable = false))
    private Set<PharmacyBranch> branchUserAssignments = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Order> orders = new ArrayList<>();
}
