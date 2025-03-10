package com.bush.pharmacy_web_app.repository.entity;

import com.bush.pharmacy_web_app.repository.StorageRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class StorageTest {
    @Autowired
    private StorageRepository storageRepository;

    @Test
    public void getStorageById() {
        var record = storageRepository.findById(2L);

        Assertions.assertFalse(record.isEmpty());
    }

    @Test
    public void getItemsByBranch() {
        var records = storageRepository.findByBranchId(9);

        Assertions.assertFalse(records.isEmpty());
    }

    @Test
    public void getItemsByBranchAndMedicine() {
        var records = storageRepository.findByBranchIdAndMedicineId(9, 9L);

        records.forEach(System.out::println);
        Assertions.assertFalse(records.isEmpty());
    }
}
