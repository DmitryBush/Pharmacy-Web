package com.bush.pharmacy_web_app.repository.user.cart;

import com.bush.pharmacy_web_app.model.entity.cart.CartItemId;
import com.bush.pharmacy_web_app.model.entity.cart.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItems, CartItemId> {
    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = """
            INSERT INTO cart_items(f_key_cart_id, f_key_product_id, obj_amount)
            VALUES(:cartId, :productId, :quantity)
            ON CONFLICT (f_key_cart_id, f_key_product_id) DO UPDATE SET obj_amount = :quantity
            """, nativeQuery = true)
    void changeItemQuantity(@Param("cartId") Long cartId,
                            @Param("productId") Long productId,
                            @Param("quantity") Integer quantity);
    @Query("delete from CartItems ci " +
            "where ci.id.cart.id = :cartId and ci.id.product.id = :productId")
    int deleteItemByCartIdAndProductId(@Param("cartId") Long cartId, @Param("productId") Long productId);
}
