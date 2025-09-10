package com.bush.pharmacy_web_app.service;

import com.bush.pharmacy_web_app.model.entity.order.state.OrderEvent;
import com.bush.pharmacy_web_app.service.order.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Test
    @Transactional
    @Rollback
    void testProcessEvent() {
        var result = orderService.processEvent(17L, OrderEvent.OPERATOR_COMPLETES_ORDER);

        Assertions.assertTrue(result);
    }

    @Test
    @Transactional
    @Rollback
    void testInvalidStateTransition() {
        var result = orderService.processEvent(16L, OrderEvent.LOGISTIC_ISSUE);

        Assertions.assertFalse(result);
    }
}