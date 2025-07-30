package com.bush.pharmacy_web_app.service.medicine;

import com.bush.pharmacy_web_app.repository.medicine.MedicineTypeRepository;
import com.bush.pharmacy_web_app.model.dto.medicine.MedicineTypeDto;
import com.bush.pharmacy_web_app.service.medicine.mapper.MedicineTypeReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicineTypeService {
    private final MedicineTypeRepository medicineTypeRepository;

    private final MedicineTypeReadMapper typeReadMapper;

    public List<MedicineTypeDto> findAllTypes() {
        return medicineTypeRepository.findAllDistinctTypes().stream()
                .map(typeReadMapper::map)
                .toList();
    }

    public List<MedicineTypeDto> searchTypesByName(String type) {
        return medicineTypeRepository.findByTypeContainingIgnoreCaseAndParentIsNotNull(type).stream()
                .map(typeReadMapper::map)
                .toList();
    }

    public List<MedicineTypeDto> searchParentTypesByName(String type) {
        return medicineTypeRepository.findByTypeContainingIgnoreCase(type).stream()
                .map(typeReadMapper::map)
                .toList();
    }

    public List<MedicineTypeDto> findAllTypesByParent(String parent) {
        return medicineTypeRepository.findByParentType(parent)
                .stream()
                .map(typeReadMapper::map)
                .toList();
    }
}
