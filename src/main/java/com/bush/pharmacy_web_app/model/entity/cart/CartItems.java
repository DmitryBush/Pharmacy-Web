package com.bush.pharmacy_web_app.model.entity.cart;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "cart_items", uniqueConstraints = {
        @UniqueConstraint(name = "shopping_carts_pkey", columnNames = {"f_key_product_id", "f_key_cart_id"})
})
public class CartItems {
    @EmbeddedId
    private CartItemId id;
    @Column(name = "obj_amount")
    private Integer amount;
}
