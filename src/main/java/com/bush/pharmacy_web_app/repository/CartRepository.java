package com.bush.pharmacy_web_app.repository;

import com.bush.pharmacy_web_app.repository.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
