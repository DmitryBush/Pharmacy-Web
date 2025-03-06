package com.bush.pharmacy_web_app.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
