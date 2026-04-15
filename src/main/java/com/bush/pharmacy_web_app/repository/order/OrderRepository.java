package com.bush.pharmacy_web_app.repository.order;

import com.bush.pharmacy_web_app.model.entity.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    Slice<Order> findByUserMobilePhone(String mobilePhone, Pageable pageable);

    @Query(value = "SELECT o FROM Order o LEFT JOIN o.branch b WHERE b.id = :branchId",
            countQuery = "SELECT COUNT(o) FROM Order o where o.branch.id = :branchId")
    Page<Order> findByBranchId(@Param("branchId") Long branchId, Pageable pageable);
    @Query(value = "select o from Order o where o.user.id = :userId",
            countQuery = "select count(o) from Order o where o.user.id = :userId")
    Page<Order> findAllUserOrders(@Param("userId") String userId, Pageable pageable);
}
