package com.bush.pharmacy_web_app.service.medicine;

import com.bush.pharmacy_web_app.model.dto.medicine.MedicineTypeUpdateDto;
import com.bush.pharmacy_web_app.repository.medicine.MedicineTypeRepository;
import com.bush.pharmacy_web_app.model.dto.medicine.MedicineTypeDto;
import com.bush.pharmacy_web_app.service.medicine.mapper.MedicineTypeCreateMapper;
import com.bush.pharmacy_web_app.service.medicine.mapper.MedicineTypeReadMapper;
import com.bush.pharmacy_web_app.service.medicine.mapper.MedicineTypeUpdateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedicineTypeService {
    private final MedicineTypeRepository medicineTypeRepository;

    private final MedicineTypeReadMapper typeReadMapper;
    private final MedicineTypeCreateMapper typeCreateMapper;
    private final MedicineTypeUpdateMapper typeUpdateMapper;

    public List<MedicineTypeDto> findAllTypes() {
        return medicineTypeRepository.findAllDistinctTypes().stream()
                .map(typeReadMapper::map)
                .toList();
    }

    public List<MedicineTypeDto> searchTypesByName(String type) {
        return medicineTypeRepository.findByTypeContainingIgnoreCaseAndParentIsNotNull(type).stream()
                .map(typeReadMapper::map)
                .toList();
    }

    public List<MedicineTypeDto> searchParentTypesByName(String type) {
        return medicineTypeRepository.findByTypeContainingIgnoreCase(type).stream()
                .map(typeReadMapper::map)
                .toList();
    }

    public List<MedicineTypeDto> findAllTypesByParent(String parent) {
        return medicineTypeRepository.findByParentType(parent)
                .stream()
                .map(typeReadMapper::map)
                .toList();
    }

    @Transactional
    public Optional<MedicineTypeDto> createDto(MedicineTypeDto createDto) {
        return Optional.ofNullable(createDto)
                .map(typeCreateMapper::map)
                .map(medicineTypeRepository::save)
                .map(typeReadMapper::map);
    }

    @Transactional
    public Optional<MedicineTypeDto> updatePartlyType(Integer id, MedicineTypeUpdateDto updateDto) {
        return medicineTypeRepository.findById(id)
                .map(type -> typeUpdateMapper.map(updateDto, type))
                .map(medicineTypeRepository::saveAndFlush)
                .map(typeReadMapper::map);
    }
}
