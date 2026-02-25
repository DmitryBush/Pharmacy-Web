package com.bush.pharmacy_web_app.repository.manufacturer.filter;

import com.bush.pharmacy_web_app.model.dto.manufacturer.ManufacturerCountProductFilterResponse;
import com.bush.pharmacy_web_app.repository.medicine.filter.MedicineFilter;

import java.util.List;

public interface ManufacturerProductCountRepository {
    List<ManufacturerCountProductFilterResponse> findAllManufacturersByProductFilter(MedicineFilter filter);
}
