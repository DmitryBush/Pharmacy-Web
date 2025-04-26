package com.bush.pharmacy_web_app.service;

import com.bush.pharmacy_web_app.repository.MedicineImageRepository;
import com.bush.pharmacy_web_app.repository.MedicineRepository;
import com.bush.pharmacy_web_app.repository.PharmacyBranchRepository;
import com.bush.pharmacy_web_app.repository.dto.admin.MedicineAdminReadDto;
import com.bush.pharmacy_web_app.repository.dto.catalog.MedicineCreateDto;
import com.bush.pharmacy_web_app.repository.dto.catalog.MedicineManufacturer;
import com.bush.pharmacy_web_app.repository.dto.catalog.MedicineTypeDto;
import com.bush.pharmacy_web_app.repository.dto.orders.PharmacyBranchReadDto;
import com.bush.pharmacy_web_app.repository.entity.medicine.MedicineImage;
import com.bush.pharmacy_web_app.repository.filter.MedicineFilter;
import com.bush.pharmacy_web_app.repository.dto.orders.MedicineReadDto;
import com.bush.pharmacy_web_app.repository.mapper.medicine.MedicineCreateMapper;
import com.bush.pharmacy_web_app.repository.mapper.admin.MedicineAdminReadMapper;
import com.bush.pharmacy_web_app.repository.mapper.orders.MedicineReadMapper;
import com.bush.pharmacy_web_app.repository.mapper.orders.PharmacyBranchReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedicineService {
    private final MedicineRepository medicineRepository;
    private final PharmacyBranchRepository branchRepository;
    private final MedicineImageRepository imageRepository;

    private final MedicineReadMapper medicineReadMapper;
    private final MedicineCreateMapper medicineCreateMapper;
    private final PharmacyBranchReadMapper branchReadMapper;
    private final MedicineAdminReadMapper adminMedicineReadMapper;

    private final FileSystemStorageService storageService;

    public List<MedicineReadDto> findAll() {
        return medicineRepository.findAll().stream()
                .map(medicineReadMapper::map)
                .toList();
    }

    public Page<MedicineReadDto> findAll(MedicineFilter filter, Pageable pageable) {
        return medicineRepository.findAllByFilter(filter, pageable)
                .map(medicineReadMapper::map);
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

    public Optional<MedicineReadDto> findDtoById(Long id) {
        return medicineRepository.findById(id)
                .map(medicineReadMapper::map);
    }

    public Optional<MedicineAdminReadDto> findAdminDtoById(Long id) {
        return medicineRepository.findById(id)
                .map(adminMedicineReadMapper::map);
    }

    public List<MedicineReadDto> findByContainingName(String name) {
        return medicineRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(medicineReadMapper::map)
                .toList();
    }

    public List<PharmacyBranchReadDto> findBranchesMedicineLocated(Long medicineId) {
        return branchRepository.findBranchesMedicineLocated(medicineId)
                .stream()
                .map(branchReadMapper::map)
                .toList();
    }

    @Transactional
    public Optional<MedicineReadDto> createMedicine(MedicineCreateDto createDto) {
        return Optional.ofNullable(createDto)
                .map(dto -> {
//                    storeMedicineImage(dto.images());
                    return medicineCreateMapper.map(dto);
                })
                .map(medicineRepository::save)
                .map(medicineReadMapper::map);
    }
    @Transactional
    public Optional<MedicineReadDto> updateMedicine(Long id, MedicineCreateDto createDto, List<MultipartFile> images) {
        return medicineRepository.findById(id)
                .map(lamb -> {
                    var mergedDto = MedicineCreateDto.IMAGES_STRATEGY_MERGE
                            .merge(MedicineCreateDto.builder().images(images).build(), createDto);
                    storeMedicineImage(mergedDto.images());
                    return medicineCreateMapper.map(mergedDto, lamb);
                })
                .map(medicineRepository::saveAndFlush)
                .map(medicineReadMapper::map);
    }
    @Transactional
    public boolean deleteMedicine(Long id) {
        return medicineRepository.findById(id)
                .map(medicine -> {
                    medicineRepository.delete(medicine);
                    return true;
                })
                .orElse(false);
    }

    private void storeMedicineImage(List<MultipartFile> file) {
        for (var i : file) {
            if (!i.isEmpty())
                storageService.store(i);
        }
    }

    public Optional<Resource> findImageByIdAndFilename(Long id, String filename) {
        return imageRepository.findByMedicineIdAndPath(id, filename)
                .map(MedicineImage::getPath)
                .map(storageService::loadAsResource);
    }
}
