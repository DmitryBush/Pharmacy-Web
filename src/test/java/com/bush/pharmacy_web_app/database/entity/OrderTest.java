package com.bush.pharmacy_web_app.database.entity;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Transactional
public class OrderTest {
    private final EntityManager entityManager;

    @Autowired
    public OrderTest(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Test
    public void getOrderById() {
        var order = entityManager.find(Order.class, 5);
        System.out.println(order);

        Assertions.assertNotNull(order);
    }
}
