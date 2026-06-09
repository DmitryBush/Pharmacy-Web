package com.bush.pharmacy_web_app.model.entity.branch.transaction;

import com.bush.pharmacy_web_app.model.entity.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "transaction_items")
public class TransactionItem {
    @EmbeddedId
    @EqualsAndHashCode.Include
    private TransactionItemId id;

    @MapsId("productId")
    @ManyToOne
    @JoinColumn(name = "f_key_product_id")
    private Product product;
    @MapsId("transactionId")
    @ManyToOne
    @JoinColumn(name = "f_key_transaction_id")
    private TransactionHistory transaction;

    @Column(nullable = false)
    private Integer amount;
    @Column(nullable = false)
    private BigDecimal price;
}
