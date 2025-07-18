package com.bush.pharmacy_web_app.repository.entity.branch;

import com.bush.pharmacy_web_app.repository.entity.Address;
import com.bush.pharmacy_web_app.repository.entity.StorageItems;
import com.bush.pharmacy_web_app.repository.entity.order.Order;
import com.bush.pharmacy_web_app.repository.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"items", "orders", "address"})
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

    @OneToMany(mappedBy = "branch", fetch = FetchType.LAZY)
    private List<StorageItems> items = new ArrayList<>();
    @OneToMany(mappedBy = "branch", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
    @ManyToMany(mappedBy = "branchUserAssignments")
    private Set<User> userBranchAssigned = new HashSet<>();
}
