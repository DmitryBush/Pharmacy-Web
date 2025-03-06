package com.bush.pharmacy_web_app.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long id;
    @Column(name = "status_order")
    private Short statusOrder;
    @Column(nullable = false)
    private Instant date;

    @ManyToOne
    @JoinColumn(name = "f_key_customer_id", nullable = false)
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "f_key_branch_id")
    private PharmacyBranch branch;
    @Builder.Default
    @OneToMany(mappedBy = "order")
    private List<CartItems> cartItems = new ArrayList<>();
}
