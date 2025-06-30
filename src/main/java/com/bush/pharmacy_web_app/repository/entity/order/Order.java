package com.bush.pharmacy_web_app.repository.entity.order;

import com.bush.pharmacy_web_app.repository.entity.PharmacyBranch;
import com.bush.pharmacy_web_app.repository.entity.User;
import com.bush.pharmacy_web_app.repository.entity.order.state.OrderState;
import com.bush.pharmacy_web_app.repository.entity.order.state.OrderStatus;
import com.bush.pharmacy_web_app.repository.entity.order.state.factory.FactoryOrderState;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Builder
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id", nullable = false)
    private Long id;

    @Enumerated
    @Column(name = "status_order")
    private OrderStatus status;
    @Transient
    @Setter(AccessLevel.NONE)
    private OrderState orderState;

    @Column(nullable = false)
    private Instant date;

    @ManyToOne
    @JoinColumn(name = "f_key_customer_id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "f_key_branch_id", nullable = false)
    private PharmacyBranch branch;
    @OneToMany(mappedBy = "order")
    @Builder.Default
    private List<OrderItem> orderItemList = new ArrayList<>();

    @Builder
    public Order(Instant date, List<OrderItem> orderItemList, PharmacyBranch branch,
                 User user, OrderStatus status, Long id) {
        this.date = date;
        this.orderItemList = orderItemList;
        this.branch = branch;
        this.user = user;
        this.status = status;
        this.id = id;
    }

    @PostLoad
    private void initState() {
        orderState = FactoryOrderState.getState(status);
    }

    public void setStatus(OrderStatus status) {
        if (this.status != null) {
            if (status.equals(OrderStatus.COMPLETED))
                this.status = orderState.completeOrder();
            else if (status.equals(OrderStatus.CANCELED))
                this.status = orderState.cancelOrder();
            else
                this.status = orderState.completePhase();
        }
        orderState = FactoryOrderState.getState(status);
    }
}
