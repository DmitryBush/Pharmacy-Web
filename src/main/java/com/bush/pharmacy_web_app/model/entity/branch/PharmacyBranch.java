package com.bush.pharmacy_web_app.model.entity.branch;

import com.bush.pharmacy_web_app.model.entity.Address;
import com.bush.pharmacy_web_app.model.entity.order.Order;
import com.bush.pharmacy_web_app.model.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"items", "orders", "address", "supervisor", "userBranchAssigned"})
@ToString(exclude = {"items", "orders", "address", "supervisor", "userBranchAssigned"})
@Entity
@Table(name = "pharmacy_branches")
public class PharmacyBranch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id", nullable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_key_address_id", nullable = false)
    private Address address;
    @Column(name = "warehouse_limitation", nullable = false)
    private Integer warehouseLimitations;
    @Column(name = "is_active")
    private Boolean isActive;
    @OneToOne
    @JoinColumn(name = "user_supervisor")
    private User supervisor;
    @Column(name = "branch_phone")
    private String branchPhone;
    @OneToMany(mappedBy = "branch", fetch = FetchType.LAZY)
    private List<BranchOpeningHours> openingHours = new ArrayList<>();

    @OneToMany(mappedBy = "branch", fetch = FetchType.LAZY)
    private List<StorageItems> items = new ArrayList<>();
    @OneToMany(mappedBy = "branch", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
    @ManyToMany(mappedBy = "branchUserAssignments")
    private Set<User> userBranchAssigned = new HashSet<>();
}
