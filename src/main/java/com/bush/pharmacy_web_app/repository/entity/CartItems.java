package com.bush.pharmacy_web_app.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"order"})
@Builder
@Entity
@Table(name = "shopping_carts")
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "obj_id", nullable = false)
    private Long id;
    @Column(name = "obj_amount")
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "f_key_medicine_id", nullable = false)
    private Medicine medicine;
    @ManyToOne
    @JoinColumn(name = "f_key_order_id", nullable = false)
    private Order order;
}
