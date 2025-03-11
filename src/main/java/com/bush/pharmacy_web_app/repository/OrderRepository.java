package com.bush.pharmacy_web_app.repository;

import com.bush.pharmacy_web_app.repository.entity.CartItems;
import com.bush.pharmacy_web_app.repository.entity.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Slice<Order> findByCustomerMobilePhone(String mobilePhone, Pageable pageable);
}
