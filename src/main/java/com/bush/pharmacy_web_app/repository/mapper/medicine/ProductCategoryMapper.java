package com.bush.pharmacy_web_app.repository.mapper.medicine;

import com.bush.pharmacy_web_app.repository.MedicineTypeRepository;
import com.bush.pharmacy_web_app.repository.dto.medicine.ProductCategoryDto;
import com.bush.pharmacy_web_app.repository.entity.medicine.ProductCategories;
import com.bush.pharmacy_web_app.repository.entity.medicine.ProductCategoriesId;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductCategoryMapper implements DtoMapper<List<ProductCategoryDto>, List<ProductCategories>> {
    private final MedicineTypeCreateMapper typeCreateMapper;
    @Override
    public List<ProductCategories> map(List<ProductCategoryDto> obj) {
        return obj.stream()
                .map(productCategoryDto -> copyObj(productCategoryDto, new ProductCategories()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private ProductCategories copyObj(ProductCategoryDto fromObj, ProductCategories toObj) {
        return Optional.ofNullable(fromObj.type())
                .map(typeCreateMapper::map)
                .map(type -> {
                    toObj.setId(ProductCategoriesId.builder()
                            .type(type)
                            .build());
                    toObj.setIsMain(fromObj.isMain());
                    return toObj;
                })
                .orElseThrow();
    }
}
