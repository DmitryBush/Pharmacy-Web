package com.bush.pharmacy_web_app.repository.filter;

import com.bush.pharmacy_web_app.repository.entity.Medicine;

import java.util.List;

public interface FilterMedicineRepository {
    List<Medicine> findAllByFilter(MedicineFilter filter);
}
