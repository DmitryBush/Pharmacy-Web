package com.bush.pharmacy_web_app.service.medicine.mapper;

import com.bush.pharmacy_web_app.model.entity.medicine.MedicineImage;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

@Component
public class MedicineImageCreateMapper implements DtoMapper<MultipartFile, MedicineImage> {
    @Override
    public MedicineImage map(MultipartFile obj) {
        return copyObj(obj, new MedicineImage());
    }

    @Override
    public MedicineImage map(MultipartFile fromObj, MedicineImage toObj) {
        return copyObj(fromObj, toObj);
    }

    private MedicineImage copyObj(MultipartFile fromObj, MedicineImage toObj) {
        Optional.ofNullable(fromObj)
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(image -> toObj.setPath(image.getOriginalFilename()));
        return toObj;
    }
}
