package com.bush.pharmacy_web_app.model.entity.user;

import com.bush.pharmacy_web_app.model.entity.branch.PharmacyBranch;
import com.bush.pharmacy_web_app.model.entity.order.Order;
import com.bush.pharmacy_web_app.model.entity.user.role.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"orders", "branchUserAssignments"})
@ToString(exclude = {"orders", "branchUserAssignments"})
@Builder
@Entity
@Table(name = "users")
@DynamicInsert
public class User {
    @Id
    @Column(name = "mobile_phone", nullable = false, length = 18)
    private String mobilePhone;
    @Column(nullable = false, length = 25)
    private String name;
    @Column(nullable = false, length = 25)
    private String surname;
    @Column(name = "last_name", length = 25)
    private String lastName;
    @Column(nullable = false, length = 256)
    private String password;
    @ManyToOne
    @JoinColumn(name = "f_key_role_id")
    @ColumnDefault("3")
    private Role role;
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
