package com.bush.pharmacy_web_app.service;

import com.bush.pharmacy_web_app.model.entity.order.Order;
import com.bush.pharmacy_web_app.model.entity.order.state.OrderEvent;
import com.bush.pharmacy_web_app.service.order.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
class OrderServiceTest {
    @Autowired
    private OrderService orderService;

    @Test
    @Transactional
    @Rollback
    void testProcessEvent() {

    }

    @Test
    @Transactional
    @Rollback
    void testInvalidStateTransition() {

    }
}