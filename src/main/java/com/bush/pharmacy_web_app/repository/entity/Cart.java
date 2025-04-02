package com.bush.pharmacy_web_app.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "f_key_customer_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "cart")
    private List<CartItems> cartItemsList = new ArrayList<>();
}
