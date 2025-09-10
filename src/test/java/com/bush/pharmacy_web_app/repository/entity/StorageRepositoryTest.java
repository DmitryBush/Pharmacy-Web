package com.bush.pharmacy_web_app.repository.entity;

import com.bush.pharmacy_web_app.repository.branch.StorageRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

// Test temporarily disabled for CI due to local database
@Tag("DatabaseRequired")
@SpringBootTest
@Transactional
public class StorageRepositoryTest {
    @Autowired
    private StorageRepository storageRepository;

    @Test
    public void getStorageById() {
        var record = storageRepository.findById(2L);

        Assertions.assertFalse(record.isEmpty());
    }

    @Test
    public void getItemsByBranch() {
        var records = storageRepository.findByBranchId(9L);

        Assertions.assertFalse(records.isEmpty());
    }

    @Test
    public void getItemsByBranchAndMedicine() {
        var record = storageRepository.findByBranchIdAndMedicineId(9L, 9L);

        Assertions.assertNull(record);
    }
}
