package com.bush.pharmacy_web_app.repository.branch;

import com.bush.pharmacy_web_app.model.entity.branch.StorageItems;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

public interface StorageRepository extends JpaRepository<StorageItems, Long> {
    List<StorageItems> findByBranchId(Long id);

    Page<StorageItems> findByBranchId(Long id, Pageable pageable);

    Optional<StorageItems> findByBranchIdAndProductId(Long branchId, Long medicineId);

    @Query("select i.amount from StorageItems i " +
            "where i.product.id = :productId and i.branch.id = :branchId")
    Optional<Integer> findAmountByBranchIdAndProductId(@PathVariable Long branchId, @PathVariable Long productId);
}
