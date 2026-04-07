package com.bush.pharmacy_web_app.repository.user.cart;

import com.bush.pharmacy_web_app.model.entity.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findCartByUserMobilePhone(String mobilePhone);
}
