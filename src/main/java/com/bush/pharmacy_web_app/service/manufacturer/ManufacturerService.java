package com.bush.pharmacy_web_app.service.manufacturer;

import com.bush.pharmacy_web_app.model.dto.manufacturer.ManufacturerCountProductFilterResponse;
import com.bush.pharmacy_web_app.model.dto.manufacturer.ManufacturerCreateDto;
import com.bush.pharmacy_web_app.model.dto.manufacturer.ManufacturerReadDto;
import com.bush.pharmacy_web_app.model.entity.manufacturer.Manufacturer;
import com.bush.pharmacy_web_app.repository.product.filter.ProductFilter;
import com.bush.pharmacy_web_app.service.manufacturer.mapper.ManufacturerCreateMapper;
import com.bush.pharmacy_web_app.service.manufacturer.mapper.LegacyManufacturerReadMapper;
import com.bush.pharmacy_web_app.repository.manufacturer.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    private final LegacyManufacturerReadMapper manufacturerReadMapper;
    private final ManufacturerCreateMapper manufacturerCreateMapper;

    @Transactional(propagation = Propagation.MANDATORY)
    public ManufacturerReadDto findOrCreateManufacturer(ManufacturerCreateDto createDto) {
        return Optional.ofNullable(createDto.id())
                .flatMap(manufacturerRepository::findById)
                .map(manufacturerReadMapper::map)
                .orElseGet(() -> Optional.of(createDto)
                        .map(manufacturerCreateMapper::map)
                        .map(manufacturerRepository::save)
                        .map(manufacturerReadMapper::map)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)));
    }

    public Manufacturer getReferenceById(Long manufacturerId) {
        return manufacturerRepository.getReferenceById(manufacturerId);
    }

    public List<ManufacturerCountProductFilterResponse> findAllManufacturersCountByProductFilter(ProductFilter productFilter) {
        return manufacturerRepository.findAllManufacturersByProductFilter(productFilter);
    }

    public List<ManufacturerReadDto> findByNameContaining(String name) {
        return manufacturerRepository.findByNameContainingIgnoreCase(name)
                .stream().map(manufacturerReadMapper::map).toList();
    }
}
