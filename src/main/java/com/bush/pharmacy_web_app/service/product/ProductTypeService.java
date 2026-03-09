package com.bush.pharmacy_web_app.service.product;

import com.bush.pharmacy_web_app.model.dto.product.ProductTypeUpdateDto;
import com.bush.pharmacy_web_app.model.entity.product.ProductType;
import com.bush.pharmacy_web_app.repository.product.ProductTypeRepository;
import com.bush.pharmacy_web_app.model.dto.product.ProductTypeDto;
import com.bush.pharmacy_web_app.service.product.mapper.type.MedicineTypeCreateMapper;
import com.bush.pharmacy_web_app.service.product.mapper.type.MedicineTypeReadMapper;
import com.bush.pharmacy_web_app.service.product.mapper.type.MedicineTypeUpdateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductTypeService {
    private final ProductTypeRepository productTypeRepository;

    private final MedicineTypeReadMapper typeReadMapper;
    private final MedicineTypeCreateMapper typeCreateMapper;
    private final MedicineTypeUpdateMapper typeUpdateMapper;

    public List<ProductTypeDto> findAllTypes() {
        return productTypeRepository.findAllDistinctTypes().stream()
                .map(typeReadMapper::map)
                .toList();
    }

    public List<ProductTypeDto> searchTypesByName(String type) {
        return productTypeRepository.findByNameContainingIgnoreCaseAndParentIsNotNull(type).stream()
                .map(typeReadMapper::map)
                .toList();
    }

    public List<ProductTypeDto> searchParentTypesByName(String type) {
        return productTypeRepository.findByNameContainingIgnoreCase(type).stream()
                .map(typeReadMapper::map)
                .toList();
    }

    public List<ProductTypeDto> findAllTypesByParent(String parent) {
        return productTypeRepository.findByParentName(parent)
                .stream()
                .map(typeReadMapper::map)
                .toList();
    }

    public ProductTypeDto findByTypeName(String type) {
        return productTypeRepository.findByName(type)
                .map(typeReadMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    public ProductType getReferenceById(Integer id) {
        return productTypeRepository.getReferenceById(id);
    }

    protected ProductType findOrCreate(ProductTypeDto typeDto) {
        return productTypeRepository.findByName(typeDto.name())
                .orElseGet(() -> Optional.of(typeDto)
                        .map(typeCreateMapper::map)
                        .map(productTypeRepository::save)
                        .orElseThrow());
    }

    @Transactional
    public Optional<ProductTypeDto> createDto(ProductTypeDto createDto) {
        return Optional.ofNullable(createDto)
                .map(typeCreateMapper::map)
                .map(productTypeRepository::save)
                .map(typeReadMapper::map);
    }

    @Transactional
    public Optional<ProductTypeDto> updatePartlyType(Integer id, ProductTypeUpdateDto updateDto) {
        return productTypeRepository.findById(id)
                .map(type -> typeUpdateMapper.map(updateDto, type))
                .map(productTypeRepository::saveAndFlush)
                .map(typeReadMapper::map);
    }

    @Transactional
    public Boolean deleteType(Integer id) {
        return productTypeRepository.findById(id)
                .map(type -> {
                    productTypeRepository.delete(type);
                    return true;
                })
                .orElse(false);
    }
}
