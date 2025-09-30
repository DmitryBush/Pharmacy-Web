package com.bush.pharmacy_web_app.model.entity.branch.transaction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "transaction_items")
public class TransactionItem {
    @EmbeddedId
    private TransactionItemId id;
    @Column(nullable = false)
    private Integer amount;
    @Column(nullable = false)
    private BigDecimal price;
}
