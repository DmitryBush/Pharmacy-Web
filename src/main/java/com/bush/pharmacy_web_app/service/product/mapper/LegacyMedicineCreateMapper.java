package com.bush.pharmacy_web_app.service.product.mapper;

import com.bush.pharmacy_web_app.model.entity.product.Product;
import com.bush.pharmacy_web_app.repository.product.ProductImageRepository;
import com.bush.pharmacy_web_app.repository.product.ProductRepository;
import com.bush.pharmacy_web_app.model.dto.product.ProductCreateDto;
import com.bush.pharmacy_web_app.service.product.mapper.image.MedicineImageCreateMapper;
import com.bush.pharmacy_web_app.service.product.mapper.list.ListProductCategoryCreateMapper;
import com.bush.pharmacy_web_app.shared.mapper.DtoMapper;
import com.bush.pharmacy_web_app.service.supplier.mapper.SupplierCreateMapper;
import com.bush.pharmacy_web_app.service.manufacturer.mapper.ManufacturerCreateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LegacyMedicineCreateMapper implements DtoMapper<ProductCreateDto, Product> {
    private final SupplierCreateMapper supplierCreateMapper;
    private final ManufacturerCreateMapper manufacturerCreateMapper;
    private final ListProductCategoryCreateMapper categoryMapper;
    private final MedicineImageCreateMapper imageCreateMapper;

    private final ProductRepository productRepository;
    private final ProductImageRepository imageRepository;
    @Override
    public Product map(ProductCreateDto obj) {
        return copyObj(obj, new Product());
    }

    @Override
    public Product map(ProductCreateDto fromObj, Product toObj) {
        return copyObj(fromObj, toObj);
    }

    private Product copyObj(ProductCreateDto fromObj, Product toObj) {
        var supplier = Optional.ofNullable(fromObj.supplier())
                .map(supplierCreateMapper::map)
                .orElseThrow();
        var manufacturer = Optional.ofNullable(fromObj.manufacturer())
                .map(manufacturerCreateMapper::map)
                .orElseThrow();
        var categories = Optional.ofNullable(fromObj.type())
                .map(categoryMapper::map)
                .orElseThrow();
//        var images = Optional.ofNullable(fromObj.id())
//                .map(imageRepository::findByProductId)
//                .map(list -> {
//                    var dtoCollection = Optional.ofNullable(fromObj.images())
//                            .map(file -> file.stream()
//                                    .map(imageCreateMapper::map)
//                                    .peek(image -> image.setProduct(toObj))
//                                    .collect(Collectors.toCollection(ArrayList::new)))
//                            .orElse(new ArrayList<>());
//                    list.addAll(dtoCollection);
//                    return list;
//                })
//                .or(() -> Optional.ofNullable(toObj.getId())
//                        .map(imageRepository::findByProductId)
//                        .map(list -> {
//                            var dtoCollection = Optional.ofNullable(fromObj.images())
//                                    .map(file -> file.stream()
//                                            .filter(Predicate.not(MultipartFile::isEmpty))
//                                            .map(imageCreateMapper::map)
//                                            .peek(image -> image.setProduct(toObj))
//                                            .collect(Collectors.toCollection(ArrayList::new)))
//                                    .orElse(new ArrayList<>());
//                            list.addAll(dtoCollection);
//                            return list;
//                        }))
//                .or(() -> Optional.ofNullable(fromObj.images())
//                        .map(list -> list
//                                .stream()
//                                .map(imageCreateMapper::map)
//                                .peek(image -> image.setProduct(toObj))
//                                .collect(Collectors.toCollection(ArrayList::new)))
//                )
//                .orElse(new ArrayList<>());

        return Optional.ofNullable(fromObj.id())
                .flatMap(productRepository::findById)
                .map(medicine -> {
                    toObj.setName(fromObj.name());

                    toObj.setType(categories.stream()
                            .peek(productCategories -> {
                                var id = productCategories.getId();
                                id.setProduct(medicine);

                                productCategories.setId(id);
                            })
                            .collect(Collectors.toCollection(ArrayList::new)));

                    toObj.setManufacturer(manufacturer);
                    toObj.setPrice(fromObj.price());
                    toObj.setRecipe(fromObj.recipe());
                    toObj.setSupplier(supplier);
//                    toObj.setImage(images);

                    toObj.setActiveIngredient(fromObj.activeIngredient());
                    toObj.setExpirationDate(fromObj.expirationDate());
                    toObj.setComposition(fromObj.composition());
                    toObj.setIndication(fromObj.indication());
                    toObj.setContraindication(fromObj.contraindication());
                    toObj.setSideEffect(fromObj.sideEffect());
                    toObj.setInteraction(fromObj.interaction());
                    toObj.setAdmissionCourse(fromObj.admissionCourse());
                    toObj.setOverdose(fromObj.overdose());
                    toObj.setSpecialInstruction(fromObj.specialInstruction());
                    toObj.setStorageCondition(fromObj.storageCondition());
                    toObj.setReleaseForm(fromObj.releaseForm());
                    return toObj;
                })
                .orElseGet(() -> {
                    toObj.setName(fromObj.name());

                    toObj.setType(categories.stream()
                            .peek(productCategories -> {
                                var id = productCategories.getId();
                                id.setProduct(toObj);

                                productCategories.setId(id);
                            })
                            .collect(Collectors.toCollection(ArrayList::new)));


                    toObj.setManufacturer(manufacturer);
                    toObj.setPrice(fromObj.price());
                    toObj.setRecipe(fromObj.recipe());
                    toObj.setSupplier(supplier);
//                    toObj.setImage(images);

                    toObj.setActiveIngredient(fromObj.activeIngredient());
                    toObj.setExpirationDate(fromObj.expirationDate());
                    toObj.setComposition(fromObj.composition());
                    toObj.setIndication(fromObj.indication());
                    toObj.setContraindication(fromObj.contraindication());
                    toObj.setSideEffect(fromObj.sideEffect());
                    toObj.setInteraction(fromObj.interaction());
                    toObj.setAdmissionCourse(fromObj.admissionCourse());
                    toObj.setOverdose(fromObj.overdose());
                    toObj.setSpecialInstruction(fromObj.specialInstruction());
                    toObj.setStorageCondition(fromObj.storageCondition());
                    toObj.setReleaseForm(fromObj.releaseForm());
                    return toObj;
                });
    }
}
