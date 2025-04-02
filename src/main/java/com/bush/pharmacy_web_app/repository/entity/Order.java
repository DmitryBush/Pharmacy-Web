package com.bush.pharmacy_web_app.repository.entity;

import jakarta.persistence.*;
import lombok.*;

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
    private User user;
    @ManyToOne
    @JoinColumn(name = "f_key_branch_id")
    private PharmacyBranch branch;
    @OneToOne
    @JoinColumn(name = "f_key_cart_id")
    private Cart cart;
}
