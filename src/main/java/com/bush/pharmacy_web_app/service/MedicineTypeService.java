package com.bush.pharmacy_web_app.service;

import com.bush.pharmacy_web_app.repository.MedicineTypeRepository;
import com.bush.pharmacy_web_app.repository.dto.medicine.MedicineTypeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicineTypeService {
    private final MedicineTypeRepository medicineTypeRepository;

    public List<MedicineTypeDto> findAllTypes() {
        return medicineTypeRepository.findAllDistinctTypes().stream()
                .map(type -> new MedicineTypeDto(type.getType(), type.getParentId().getId()))
                .toList();
    }
}
