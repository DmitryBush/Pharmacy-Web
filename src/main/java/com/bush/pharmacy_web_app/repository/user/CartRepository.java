package com.bush.pharmacy_web_app.repository.user;

import com.bush.pharmacy_web_app.model.entity.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
