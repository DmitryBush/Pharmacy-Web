package com.bush.pharmacy_web_app.service;

import com.bush.pharmacy_web_app.repository.MedicineRepository;
import com.bush.pharmacy_web_app.repository.dto.catalog.MedicineCreateDto;
import com.bush.pharmacy_web_app.repository.dto.catalog.MedicineManufacturer;
import com.bush.pharmacy_web_app.repository.dto.catalog.MedicineTypeDto;
import com.bush.pharmacy_web_app.repository.filter.MedicineFilter;
import com.bush.pharmacy_web_app.repository.dto.orders.MedicineReadDto;
import com.bush.pharmacy_web_app.repository.mapper.MedicineCreateMapper;
import com.bush.pharmacy_web_app.repository.mapper.orders.MedicineReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedicineService {
    private final MedicineRepository medicineRepository;
    private final MedicineReadMapper readMapper;
    private final MedicineCreateMapper createMapper;

    public List<MedicineReadDto> findAll() {
        return medicineRepository.findAll().stream()
                .map(readMapper::map)
                .toList();
    }

    public List<MedicineReadDto> findAll(MedicineFilter filter) {
        return medicineRepository.findAllByFilter(filter).stream()
                .map(readMapper::map)
                .toList();
    }

    public List<MedicineTypeDto> findAllTypes() {
        return medicineRepository.findDistinctMedicineType().stream()
                .map(MedicineTypeDto::new)
                .toList();
    }

    public List<MedicineManufacturer> findAllManufacturers() {
        return medicineRepository.findDistinctMedicineManufacturer().stream()
                .map(MedicineManufacturer::new)
                .toList();
    }

    public List<MedicineReadDto> findById(Integer id) {
        return medicineRepository.findById(id).stream()
                .map(readMapper::map)
                .toList();
    }

    public Optional<MedicineReadDto> createMedicine(MedicineCreateDto createDto) {
        return Optional.ofNullable(createDto)
                .map(createMapper::map)
                .map(medicineRepository::save)
                .map(readMapper::map);
    }
    @Transactional
    public Optional<MedicineReadDto> updateMedicine(Integer id, MedicineCreateDto createDto) {
        return medicineRepository.findById(id)
                .map(lamb -> createMapper.map(createDto, lamb))
                .map(medicineRepository::saveAndFlush)
                .map(readMapper::map);
    }
    @Transactional
    public boolean deleteMedicine(Integer id) {
        return medicineRepository.findById(id)
                .map(lamb -> {
                    medicineRepository.deleteById(id);
                    medicineRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
