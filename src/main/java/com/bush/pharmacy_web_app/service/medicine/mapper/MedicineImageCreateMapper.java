package com.bush.pharmacy_web_app.service.medicine.mapper;

import com.bush.pharmacy_web_app.model.entity.medicine.ProductImage;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.function.Predicate;

@Component
public class MedicineImageCreateMapper implements DtoMapper<MultipartFile, ProductImage> {
    @Override
    public ProductImage map(MultipartFile obj) {
        return copyObj(obj, new ProductImage());
    }

    @Override
    public ProductImage map(MultipartFile fromObj, ProductImage toObj) {
        return copyObj(fromObj, toObj);
    }

    private ProductImage copyObj(MultipartFile fromObj, ProductImage toObj) {
        Optional.ofNullable(fromObj)
                .filter(Predicate.not(MultipartFile::isEmpty))
                .ifPresent(image -> toObj.setPath(image.getOriginalFilename()));
        return toObj;
    }
}
