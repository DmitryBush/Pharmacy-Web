package com.bush.pharmacy_web_app.service.medicine.mapper;

import com.bush.pharmacy_web_app.repository.medicine.MedicineTypeRepository;
import com.bush.pharmacy_web_app.model.dto.medicine.MedicineTypeDto;
import com.bush.pharmacy_web_app.model.entity.medicine.MedicineType;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ListMedicineTypeCreateMapper implements DtoMapper<List<MedicineTypeDto>, List<MedicineType>> {
    private final MedicineTypeRepository medicineTypeRepository;
    @Override
    public List<MedicineType> map(List<MedicineTypeDto> obj) {
        return obj.stream()
                .map(medicineTypeDto -> copyObj(medicineTypeDto, new MedicineType()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private MedicineType copyObj(MedicineTypeDto fromObj, MedicineType toObj) {
        return medicineTypeRepository.findByType(fromObj.name())
                .orElseGet(() -> {
                    var parent = medicineTypeRepository.findByType(fromObj.parent())
                                    .orElseThrow();

                    toObj.setType(fromObj.name());
                    toObj.setParent(parent);
                    toObj.setChildTypes(Collections.emptyList());
                    return toObj;
                });
    }
}
