package com.bush.pharmacy_web_app.service.medicine;

import com.bush.pharmacy_web_app.model.dto.medicine.MedicineTypeUpdateDto;
import com.bush.pharmacy_web_app.model.entity.medicine.MedicineType;
import com.bush.pharmacy_web_app.repository.medicine.MedicineTypeRepository;
import com.bush.pharmacy_web_app.model.dto.medicine.MedicineTypeDto;
import com.bush.pharmacy_web_app.service.medicine.mapper.type.MedicineTypeCreateMapper;
import com.bush.pharmacy_web_app.service.medicine.mapper.type.MedicineTypeReadMapper;
import com.bush.pharmacy_web_app.service.medicine.mapper.type.MedicineTypeUpdateMapper;
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
        return medicineTypeRepository.findByNameContainingIgnoreCaseAndParentIsNotNull(type).stream()
                .map(typeReadMapper::map)
                .toList();
    }

    public List<MedicineTypeDto> searchParentTypesByName(String type) {
        return medicineTypeRepository.findByNameContainingIgnoreCase(type).stream()
                .map(typeReadMapper::map)
                .toList();
    }

    public List<MedicineTypeDto> findAllTypesByParent(String parent) {
        return medicineTypeRepository.findByParentName(parent)
                .stream()
                .map(typeReadMapper::map)
                .toList();
    }

    public MedicineTypeDto findByTypeName(String type) {
        return medicineTypeRepository.findByName(type)
                .map(typeReadMapper::map)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    public MedicineType getReferenceById(Integer id) {
        return medicineTypeRepository.getReferenceById(id);
    }

    protected MedicineType findOrCreate(MedicineTypeDto typeDto) {
        return medicineTypeRepository.findByName(typeDto.name())
                .orElseGet(() -> Optional.of(typeDto)
                        .map(typeCreateMapper::map)
                        .map(medicineTypeRepository::save)
                        .orElseThrow());
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

    @Transactional
    public Boolean deleteType(Integer id) {
        return medicineTypeRepository.findById(id)
                .map(type -> {
                    medicineTypeRepository.delete(type);
                    return true;
                })
                .orElse(false);
    }
}
