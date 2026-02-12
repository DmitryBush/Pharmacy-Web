package com.bush.pharmacy_web_app.service.manufacturer;

import com.bush.pharmacy_web_app.model.dto.manufacturer.ManufacturerCountProductFilterResponse;
import com.bush.pharmacy_web_app.model.dto.manufacturer.ManufacturerReadDto;
import com.bush.pharmacy_web_app.repository.medicine.filter.MedicineFilter;
import com.bush.pharmacy_web_app.service.manufacturer.mapper.ManufacturerReadMapper;
import com.bush.pharmacy_web_app.repository.manufacturer.ManufacturerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;
    private final ManufacturerReadMapper manufacturerReadMapper;

    public List<ManufacturerCountProductFilterResponse> findAllManufacturersCountByProductFilter(MedicineFilter medicineFilter) {
        return manufacturerRepository.findAllManufacturersByProductFilter(medicineFilter);
    }

    public List<ManufacturerReadDto> findByNameContaining(String name) {
        return manufacturerRepository.findByNameContainingIgnoreCase(name)
                .stream().map(manufacturerReadMapper::map).toList();
    }
}
