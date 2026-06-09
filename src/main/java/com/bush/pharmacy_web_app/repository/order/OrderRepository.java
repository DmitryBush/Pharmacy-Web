package com.bush.pharmacy_web_app.repository.order;

import com.bush.pharmacy_web_app.model.entity.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    Slice<Order> findByUserMobilePhone(String mobilePhone, Pageable pageable);

    @Query(value = "SELECT o FROM Order o LEFT JOIN o.branch b WHERE b.id = :branchId",
            countQuery = "SELECT COUNT(o) FROM Order o where o.branch.id = :branchId")
    Page<Order> findByBranchId(@Param("branchId") Long branchId, Pageable pageable);

    @Query("select o.id from Order o where o.user.id = :userId")
    List<UUID> findAllUserOrdersIdList(@Param("userId") String userId, Pageable pageable);

    @Query("select count(o) from Order o where o.user.id = :userId")
    Long countUserOrders(@Param("userId") String userId);

    @Query("select o " +
            "from Order o " +
            "join fetch o.orderItemList oi " +
            "join fetch oi.product p " +
            "join fetch p.manufacturer pm " +
            "join fetch pm.country c " +
            "where o.id in :idList")
    List<Order> findAllOrdersByIdList(@Param("idList") List<UUID> idList);
}
