package com.bush.pharmacy_web_app.service.medicine.mapper.type;

import com.bush.pharmacy_web_app.model.dto.medicine.MedicineTypeUpdateDto;
import com.bush.pharmacy_web_app.model.entity.medicine.MedicineType;
import com.bush.pharmacy_web_app.repository.medicine.MedicineTypeRepository;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MedicineTypeUpdateMapper implements DtoMapper<MedicineTypeUpdateDto, MedicineType> {
    private final MedicineTypeRepository medicineTypeRepository;
    @Override
    public MedicineType map(MedicineTypeUpdateDto obj) {
        return copyObj(obj, new MedicineType());
    }

    @Override
    public MedicineType map(MedicineTypeUpdateDto fromObj, MedicineType toObj) {
        return copyObj(fromObj, toObj);
    }

    private MedicineType copyObj(MedicineTypeUpdateDto fromObj, MedicineType toObj) {
        Optional.ofNullable(fromObj.type())
                .ifPresent(toObj::setName);
        Optional.ofNullable(fromObj.slug())
                .ifPresent(toObj::setSlug);
        Optional.ofNullable(fromObj.parent())
                .map(medicineTypeRepository::findByName)
                .ifPresentOrElse(parent ->
                        parent.ifPresentOrElse(toObj::setParent, parent::orElseThrow), () -> toObj.setParent(null));
        return toObj;
    }
}
