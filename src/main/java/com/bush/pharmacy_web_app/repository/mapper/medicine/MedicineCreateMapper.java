package com.bush.pharmacy_web_app.repository.mapper.medicine;

import com.bush.pharmacy_web_app.repository.MedicineImageRepository;
import com.bush.pharmacy_web_app.repository.MedicineRepository;
import com.bush.pharmacy_web_app.repository.TypeRepository;
import com.bush.pharmacy_web_app.repository.dto.medicine.MedicineCreateDto;
import com.bush.pharmacy_web_app.repository.entity.medicine.Medicine;
import com.bush.pharmacy_web_app.repository.mapper.DtoMapper;
import com.bush.pharmacy_web_app.repository.mapper.SupplierCreateMapper;
import com.bush.pharmacy_web_app.repository.mapper.manufacturer.ManufacturerCreateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MedicineCreateMapper implements DtoMapper<MedicineCreateDto, Medicine> {
    private final SupplierCreateMapper supplierCreateMapper;
    private final ManufacturerCreateMapper manufacturerCreateMapper;
    private final ProductCategoryMapper categoryMapper;
    private final MedicineImageCreateMapper imageCreateMapper;

    private final TypeRepository typeRepository;
    private final MedicineRepository medicineRepository;
    private final MedicineImageRepository imageRepository;
    @Override
    public Medicine map(MedicineCreateDto obj) {
        return copyObj(obj, new Medicine());
    }

    @Override
    public Medicine map(MedicineCreateDto fromObj, Medicine toObj) {
        return copyObj(fromObj, toObj);
    }

    private Medicine copyObj(MedicineCreateDto fromObj, Medicine toObj) {
        var supplier = Optional.ofNullable(fromObj.supplier())
                .map(supplierCreateMapper::map)
                .orElseThrow();
        var manufacturer = Optional.ofNullable(fromObj.manufacturer())
                .map(manufacturerCreateMapper::map)
                .orElseThrow();
        var categories = Optional.ofNullable(fromObj.type())
                .map(categoryMapper::map)
                .orElseThrow();
        var images = Optional.ofNullable(fromObj.id())
                .map(imageRepository::findByMedicineId)
                .map(list -> {
                    var dtoCollection = Optional.ofNullable(fromObj.images())
                            .map(file -> file.stream()
                                    .map(imageCreateMapper::map)
                                    .peek(image -> image.setMedicine(toObj))
                                    .collect(Collectors.toCollection(ArrayList::new)))
                            .orElse(new ArrayList<>());
                    list.addAll(dtoCollection);
                    return list;
                })
                .or(() -> Optional.ofNullable(toObj.getId())
                        .map(imageRepository::findByMedicineId)
                        .map(list -> {
                            var dtoCollection = Optional.ofNullable(fromObj.images())
                                    .map(file -> file.stream()
                                            .filter(Predicate.not(MultipartFile::isEmpty))
                                            .map(imageCreateMapper::map)
                                            .peek(image -> image.setMedicine(toObj))
                                            .collect(Collectors.toCollection(ArrayList::new)))
                                    .orElse(new ArrayList<>());
                            list.addAll(dtoCollection);
                            return list;
                        }))
                .or(() -> Optional.ofNullable(fromObj.images())
                        .map(list -> list
                                .stream()
                                .map(imageCreateMapper::map)
                                .peek(image -> image.setMedicine(toObj))
                                .collect(Collectors.toCollection(ArrayList::new)))
                )
                .orElse(new ArrayList<>());

        return Optional.ofNullable(fromObj.id())
                .flatMap(medicineRepository::findById)
                .map(medicine -> {
                    toObj.setName(fromObj.name());

                    toObj.setType(categories.stream()
                            .peek(productCategories -> {
                                var id = productCategories.getId();
                                id.setMedicine(medicine);

                                productCategories.setId(id);
                            })
                            .collect(Collectors.toCollection(ArrayList::new)));

                    toObj.setManufacturer(manufacturer);
                    toObj.setPrice(fromObj.price());
                    toObj.setRecipe(fromObj.recipe());
                    toObj.setSupplier(supplier);
                    toObj.setImage(images);

                    toObj.setActiveIngredient(fromObj.activeIngredient());
                    toObj.setExpirationDate(fromObj.expirationDate());
                    toObj.setComposition(fromObj.composition());
                    toObj.setIndication(fromObj.indication());
                    toObj.setContraindications(fromObj.contraindication());
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
                                id.setMedicine(toObj);

                                productCategories.setId(id);
                            })
                            .collect(Collectors.toCollection(ArrayList::new)));


                    toObj.setManufacturer(manufacturer);
                    toObj.setPrice(fromObj.price());
                    toObj.setRecipe(fromObj.recipe());
                    toObj.setSupplier(supplier);
                    toObj.setImage(images);

                    toObj.setActiveIngredient(fromObj.activeIngredient());
                    toObj.setExpirationDate(fromObj.expirationDate());
                    toObj.setComposition(fromObj.composition());
                    toObj.setIndication(fromObj.indication());
                    toObj.setContraindications(fromObj.contraindication());
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
