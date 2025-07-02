package com.bush.pharmacy_web_app.repository.entity.order.state;

/**
 * Represents events that trigger state transitions in an order lifecycle
 *
 * @author Dmitry Bush
 */
public enum OrderEvent {
    /**
     * <p>The time allocated for payment of the order has expired, which leads to the cancellation of the order.</p>
     * <p>Moving to the {@link OrderState#CANCELLED} state</p>
     */
    PAYMENT_TIME_EXPIRED,
    /**
     * <p>Post-payment is selected, which continues the order fulfillment.</p>
     * <p>Moving to the {@link OrderState#DECOR} state</p>
     */
    POST_PAYMENT_METHOD_SELECTED,
    /**
     * <p>Payment for the order has been successfully received.
     * This event confirms payment and moves the order to the next stage of processing.</p>
     * <p>Moving to the {@link OrderState#DECOR} state</p>
     */
    PAYMENT_RECEIVED,
    /**
     * <p>All necessary paperwork and preparation for the order have been completed.
     * This step is crucial before the order can be shipped.</p>
     * <p>Moving to the {@link OrderState#ASSEMBLY} state</p>
     */
    DECORATED_ORDER,
    /**
     * <p>The order was collected and transferred to the transport company.</p>
     * <p>Moving to the {@link OrderState#TRANSIT} state</p>
     */
    SHIPPED_ORDER,
    /**
     * <p>The order was delivered to the branch.</p>
     * <p>Moving to the {@link OrderState#DELIVERED} state</p>
     */
    DELIVERED_ORDER,
    /**
     * <p>The buyer accepts the order, and the branch operator marks it as completed.
     * This finalizes the order process.</p>
     * <p>Moving to the {@link OrderState#COMPLETED} state</p>
     */
    OPERATOR_COMPLETES_ORDER,
    /**
     * <p>Occurs when a buyer does not come to the branch to pick up the order.</p>
     * <p>Moving to the {@link OrderState#NOT_DEMAND} state</p>
     */
    OPERATOR_RETURNS_ORDER,
    /**
     * <p>The buyer independently cancels the order. This event stops further processing</p>
     * <p>Moving to the {@link OrderState#CANCELLED} state</p>
     */
    ORDER_CANCELLED_BY_USER,
    /**
     * <p>There was a problem with the order processing time.</p>
     * <p>Moving to the {@link OrderState#DEFERRED} state</p>
     */
    SELLER_ISSUE,
    /**
     * <p>There was a problem while assembling order.</p>
     * <p>Moving to the {@link OrderState#DEFERRED} state</p>
     */
    WAREHOUSE_ISSUE,
    /**
     * <p>There was a problem during the delivery of the order.</p>
     * <p>Moving to the {@link OrderState#DEFERRED} state</p>
     */
    LOGISTIC_ISSUE,
    /**
     * <p>The buyer requests a refund after receiving the order,
     * indicating dissatisfaction or an issue with the product.
     * This event starts the return and refund process.</p>
     * <p>Moving to the {@link OrderState#RETURN_REQUESTED} state</p>
     */
    RETURN_REQUESTED_BY_USER,
    /**
     * <p>The operator approved the return of the order.</p>
     * <p>Moving to the {@link OrderState#AWAITING_CUSTOMER_SHIPMENT} state</p>
     */
    RETURN_APPROVED,
    /**
     * <p>The operator disapproved the return of the order.</p>
     * <p>Moving to the {@link OrderState#RETURN_REJECTED} state</p>
     */
    RETURN_DISAPPROVED,
    /**
     * <p>The time given to the buyer to return the order has expired.</p>
     * <p>Moving to the {@link OrderState#RETURN_REJECTED} state</p>
     */
    RETURN_TIME_EXPIRED,
    /**
     * <p>The buyer sends the order back to the branch, completing their part of the return process.</p>
     * <p>Moving to the {@link OrderState#RETURN_SHIPPED_BY_CUSTOMER} state</p>
     * @see com.bush.pharmacy_web_app.repository.entity.order.state.OrderState
     */
    ORDER_RETURNED_BY_USER,
    /**
     * <p>The returned order has been collected and handed over to the transportation company for shipment back.</p>
     * <p>Moving to the {@link OrderState#RETURN_TRANSIT} state</p>
     */
    SHIPPED_RETURN,
    /**
     * <p>The returned order reaches its destination, typically the seller's location.
     * This completes the return process.</p>
     * <p>Moving to the {@link OrderState#RETURN_CLOSED} state</p>
     */
    DELIVERED_RETURN
}
