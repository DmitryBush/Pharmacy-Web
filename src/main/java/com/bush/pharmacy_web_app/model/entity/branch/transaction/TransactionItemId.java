package com.bush.pharmacy_web_app.model.entity.branch.transaction;

import com.bush.pharmacy_web_app.model.entity.medicine.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
@IdClass(TransactionItemId.class)
public class TransactionItemId {
    @ManyToOne
    @JoinColumn(name = "f_key_product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "f_key_transaction_id")
    private TransactionHistory transaction;
}
