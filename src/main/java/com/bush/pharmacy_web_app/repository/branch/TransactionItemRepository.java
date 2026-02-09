package com.bush.pharmacy_web_app.repository.branch;

import com.bush.pharmacy_web_app.model.entity.branch.transaction.TransactionItem;
import com.bush.pharmacy_web_app.model.entity.branch.transaction.TransactionItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionItemRepository extends JpaRepository<TransactionItem, TransactionItemId> {
}
