package com.bush.pharmacy_web_app.repository.entity;

import com.bush.pharmacy_web_app.repository.medicine.MedicineRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

// Test temporarily disabled for CI due to local database
@Tag("DatabaseRequired")
@SpringBootTest
@Transactional
public class ProductRepositoryTest {
    @Autowired
    private MedicineRepository medicineRepository;
    @Test
    public void findDistinctMedicineInStorage() {
        var page = PageRequest.of(0, 25);
        var medicines = medicineRepository.findDistinctMedicineStorageLocated(page);
        medicines.stream().forEach(System.out::println);

        Assertions.assertNotNull(medicines);
        Assertions.assertEquals(medicines.getContent().size(), medicines.stream().distinct().count());
    }
}
