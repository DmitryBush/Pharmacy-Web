package com.bush.pharmacy_web_app.repository.entity;

import com.bush.pharmacy_web_app.repository.entity.order.Order;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    private Integer id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_key_address_id", nullable = false)
    private Address address;

    @OneToMany(mappedBy = "branch", fetch = FetchType.LAZY)
    private List<StorageItems> items = new ArrayList<>();
    @OneToMany(mappedBy = "branch", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
}
