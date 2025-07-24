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
     * Decor, assembly or transit was frozen due to independent reason
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
     * The buyer has requested a return of the product, and this request is pending approval from the seller
     */
    RETURN_REQUESTED,
    /**
     * The return request has been reviewed and rejected by the seller.
     */
    RETURN_REJECTED,
    /**
     * The order is in a state where the seller has approved the return request, and the customer is expected
     * to ship the returned goods back to the seller. This state signifies that the return process has begun,
     * but the physical movement of goods is still pending.
     */
    AWAITING_CUSTOMER_SHIPMENT,
    /**
     * The buyer returned the order
     */
    RETURN_SHIPPED_BY_CUSTOMER,
    /**
     * The order is currently being moved to the seller
     */
    RETURN_TRANSIT,
    /**
     * The returned order has been received by the seller, finalizing the return transaction.
     */
    RETURN_CLOSED,
    /**
     * Transit was frozen due to independent reason
     */
    RETURN_DEFERRED
}
