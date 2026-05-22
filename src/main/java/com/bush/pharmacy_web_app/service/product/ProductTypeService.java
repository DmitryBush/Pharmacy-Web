package com.bush.pharmacy_web_app.service.product;

import com.bush.pharmacy_web_app.model.dto.product.ProductTypeDto;
import com.bush.pharmacy_web_app.model.dto.product.ProductTypeUpdateDto;
import com.bush.pharmacy_web_app.model.entity.product.ProductType;
import com.bush.pharmacy_web_app.repository.product.ProductTypeRepository;
import com.bush.pharmacy_web_app.service.product.mapper.type.MedicineTypeCreateMapper;
import com.bush.pharmacy_web_app.service.product.mapper.type.MedicineTypeReadMapper;
import com.bush.pharmacy_web_app.service.product.mapper.type.MedicineTypeUpdateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Cacheable(cacheNames = "productTypesByParent", key = "#parent", condition = "#parent != null")
    public List<ProductTypeDto> findAllTypesByParent(String parent) {
        return productTypeRepository.findByParentName(parent)
                .stream()
                .map(typeReadMapper::map)
                .collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "productTypeByName", key = "#type")
    public ProductTypeDto findByTypeName(String type) {
        return productTypeRepository.findByName(type)
                .map(typeReadMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    public ProductType getReferenceById(Integer id) {
        return productTypeRepository.getReferenceById(id);
    }

    @CacheEvict(cacheNames = "productTypesByParent", allEntries = true)
    @Transactional
    public Optional<ProductTypeDto> createDto(ProductTypeDto createDto) {
        return Optional.ofNullable(createDto)
                .map(typeCreateMapper::map)
                .map(productTypeRepository::save)
                .map(typeReadMapper::map);
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "productType", key = "#id"),
            @CacheEvict(cacheNames = "productTypesByParent", allEntries = true),
            @CacheEvict(cacheNames = "productTypeByName", allEntries = true)
    }
    )
    @CachePut(cacheNames = "productType", key = "#id")
    @CacheEvict(cacheNames = {"productType", "productType#parent"}, key = "#id")
    @Transactional
    public ProductTypeDto updatePartlyType(Integer id, ProductTypeUpdateDto updateDto) {
        return productTypeRepository.findById(id)
                .map(type -> typeUpdateMapper.map(updateDto, type))
                .map(productTypeRepository::saveAndFlush)
                .map(typeReadMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Caching(evict = {
            @CacheEvict(cacheNames = "productType", key = "#id"),
            @CacheEvict(cacheNames = "productTypesByParent", allEntries = true),
            @CacheEvict(cacheNames = "productTypeByName", allEntries = true)
    }
    )
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
