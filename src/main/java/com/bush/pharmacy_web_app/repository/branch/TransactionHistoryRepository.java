package com.bush.pharmacy_web_app.repository.branch;

import com.bush.pharmacy_web_app.model.dto.medicine.MedicinePreviewReadDto;
import com.bush.pharmacy_web_app.model.entity.branch.transaction.TransactionHistory;
import com.bush.pharmacy_web_app.model.entity.medicine.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
    List<TransactionHistory> findByBranchId(Long branchId);

    @Query("SELECT m, SUM(ti.amount) as totalSold " +
            "FROM TransactionItem ti " +
            "JOIN ti.id.medicine m " +
            "JOIN ti.id.transaction th " +
            "JOIN th.type tt " +
            "WHERE tt.transactionName = 'SALE' " +
            "GROUP BY m " +
            "ORDER BY totalSold DESC " +
            "LIMIT :count")
    List<Medicine> findBestSellingProducts(Integer count);
}
