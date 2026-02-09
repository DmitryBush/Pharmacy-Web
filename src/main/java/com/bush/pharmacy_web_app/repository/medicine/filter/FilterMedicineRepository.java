package com.bush.pharmacy_web_app.repository.medicine.filter;

import com.bush.pharmacy_web_app.model.entity.medicine.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilterMedicineRepository {
    Page<Product> findAllByFilter(MedicineFilter filter, Pageable pageable);
}
