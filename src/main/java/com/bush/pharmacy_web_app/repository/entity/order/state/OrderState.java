package com.bush.pharmacy_web_app.repository.entity.order.state;

/**
 * These are all the order statuses used in the system.
 * These stage's allows you to synchronize the status from the database to the backend application logic
 *
 * @author Dmitry Bush
 */
public enum OrderState {
    /**
     * Status points to user did not pay for the order or payment did not come from external IP
     */
    PAYMENT_AWAIT,
    /**
     * Order was canceled by user
     */
    CANCELLED,
    /**
     * Assembly was frozen due to independent reason
     */
    DEFERRED,
    /**
     * The order was paid for and information about this was passed on to the seller
     */
    DECOR,
    /**
     * Assembly and transfer of goods to logistics
     */
    ASSEMBLY,
    /**
     * The order is currently being moved to the pharmacy branch
     */
    TRANSIT,
    /**
     * Order was delivered to branch and awaits user
     */
    DELIVERED,
    /**
     * The transaction between the seller and the buyer was successful
     */
    COMPLETED,
    /**
     * The buyer didn't pick up the order
     */
    NOT_DEMAND,
    /**
     *
     */
    RETURN_REQUESTED,
    RETURN_REJECTED,
    AWAITING_CUSTOMER_SHIPMENT,
    RETURN_SHIPPED_BY_CUSTOMER,
    RETURN_TRANSIT,
    RETURN_CLOSED,
    RETURN_DEFERRED
}
