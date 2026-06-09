package com.bush.pharmacy_web_app.model.entity.branch.transaction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class TransactionItemId implements Serializable {
    private Long productId;
    private Long transactionId;
}
