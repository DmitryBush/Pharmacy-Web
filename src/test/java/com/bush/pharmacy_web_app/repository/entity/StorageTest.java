package com.bush.pharmacy_web_app.repository.entity;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class StorageTest {
    private final EntityManager entityManager;

    @Autowired
    public StorageTest(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Test
    public void getStorageTest() {
        var builder = entityManager.getCriteriaBuilder();
        var query = builder.createQuery(StorageItems.class);
        var root = query.from(StorageItems.class);

        query.select(root).where(builder.gt(root.get("amount"), 6));
        var records = entityManager.createQuery(query).getResultList();
        System.out.println(records);

        Assertions.assertFalse(records.isEmpty());
    }
}
