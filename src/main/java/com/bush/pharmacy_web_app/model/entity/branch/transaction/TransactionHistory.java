package com.bush.pharmacy_web_app.model.entity.branch.transaction;

import com.bush.pharmacy_web_app.model.entity.branch.PharmacyBranch;
import com.bush.pharmacy_web_app.model.entity.order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction_history")
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "completed_at", nullable = false)
    private ZonedDateTime completedAt;
    @ManyToOne
    @JoinColumn(name = "f_key_transaction_type", nullable = false)
    private TransactionType type;
    @ManyToOne
    @JoinColumn(name = "f_key_branch_id", nullable = false)
    private PharmacyBranch branch;
    @ManyToOne
    @JoinColumn(name = "f_key_order_id")
    private Order order;

    @OneToMany(mappedBy = "transaction", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<TransactionItem> items = new ArrayList<>();
}
