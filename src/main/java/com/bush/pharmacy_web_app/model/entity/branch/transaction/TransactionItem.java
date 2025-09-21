package com.bush.pharmacy_web_app.model.entity.branch.transaction;

import com.bush.pharmacy_web_app.model.entity.medicine.Medicine;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction_items")
public class TransactionItem {
    @Id
    @ManyToOne
    @JoinColumn(name = "f_key_product_id")
    private Medicine medicine;
    @Id
    @ManyToOne
    @JoinColumn(name = "f_key_transaction_id")
    private TransactionHistory transaction;
}
