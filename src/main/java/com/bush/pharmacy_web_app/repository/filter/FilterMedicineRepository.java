package com.bush.pharmacy_web_app.repository.filter;

import com.bush.pharmacy_web_app.repository.entity.Medicine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FilterMedicineRepository {
    Page<Medicine> findAllByFilter(MedicineFilter filter, Pageable pageable);
}
