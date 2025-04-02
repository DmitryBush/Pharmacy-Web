package com.bush.pharmacy_web_app.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"cart"})
@Builder
@Entity
@Table(name = "cart_items")
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "obj_id", nullable = false)
    private Long id;
    @Column(name = "obj_amount")
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_key_medicine_id", nullable = false)
    private Medicine medicine;
    @ManyToOne
    @JoinColumn(name = "f_key_cart_id")
    private Cart cart;
}
