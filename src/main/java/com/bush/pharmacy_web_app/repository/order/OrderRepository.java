package com.bush.pharmacy_web_app.repository.order;

import com.bush.pharmacy_web_app.model.entity.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Slice<Order> findByUserMobilePhone(String mobilePhone, Pageable pageable);

    @Query(value = "SELECT o FROM Order o LEFT JOIN o.branch p WHERE p.id = :id",
            countQuery = "SELECT COUNT(o) FROM Order o where o.branch.id = :id")
    Page<Order> findByBranchId(Long id, Pageable pageable);
}
