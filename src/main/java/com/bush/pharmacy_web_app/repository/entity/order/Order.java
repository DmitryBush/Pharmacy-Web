package com.bush.pharmacy_web_app.repository.entity.order;

import com.bush.pharmacy_web_app.repository.entity.PharmacyBranch;
import com.bush.pharmacy_web_app.repository.entity.User;
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
    @Enumerated
    @Column(name = "status_order")
    private OrderStatus status;
    @Column(nullable = false)
    private Instant date;

    @ManyToOne
    @JoinColumn(name = "f_key_customer_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "f_key_branch_id", nullable = false)
    private PharmacyBranch branch;
    @Builder.Default
    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItemList = new ArrayList<>();
}
