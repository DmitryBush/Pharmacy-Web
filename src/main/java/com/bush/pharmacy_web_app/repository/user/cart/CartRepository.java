package com.bush.pharmacy_web_app.repository.user.cart;

import com.bush.pharmacy_web_app.model.entity.cart.Cart;
import com.bush.pharmacy_web_app.model.entity.cart.CartItems;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("select ci from Cart c " +
            "left join c.cartItemsList ci " +
            "where c.user.mobilePhone = :mobilePhone")
    Page<CartItems> findPaginatedCartByUserMobilePhone(@Param("mobilePhone") String mobilePhone, Pageable pageable);

    @Query("select c from Cart c " +
            "where c.user.mobilePhone = :mobilePhone")
    Optional<Cart> findCartByUserMobilePhone(@Param("mobilePhone") String mobilePhone);
}
