package com.bush.pharmacy_web_app.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pharmacy_branches")
public class PharmacyBranch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id", nullable = false)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "f_key_address_id", nullable = false)
    private Address address;

    @OneToMany(mappedBy = "branch")
    private List<StorageItems> items = new ArrayList<>();
    @OneToMany(mappedBy = "branch")
    private List<Order> orders = new ArrayList<>();
}
