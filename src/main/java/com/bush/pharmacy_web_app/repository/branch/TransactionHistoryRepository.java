package com.bush.pharmacy_web_app.repository.branch;

import com.bush.pharmacy_web_app.model.entity.branch.transaction.TransactionHistory;
import com.bush.pharmacy_web_app.model.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
    List<TransactionHistory> findByBranchId(Long branchId);

    @Query("SELECT m " +
            "FROM TransactionItem ti " +
            "JOIN ti.product m " +
            "JOIN ti.transaction th " +
            "JOIN th.type tt " +
            "WHERE tt.transactionName = 'SALE' " +
            "GROUP BY m " +
            "ORDER BY SUM(ti.amount) DESC " +
            "LIMIT :count")
    List<Product> findBestSellingProducts(Integer count);
}
