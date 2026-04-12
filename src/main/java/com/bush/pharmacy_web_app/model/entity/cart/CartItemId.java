package com.bush.pharmacy_web_app.model.entity.cart;

import com.bush.pharmacy_web_app.model.entity.product.Product;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CartItemId {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "f_key_product_id", nullable = false)
    private Product product;
    @ManyToOne
    @JoinColumn(name = "f_key_cart_id", nullable = false)
    private Cart cart;
}
