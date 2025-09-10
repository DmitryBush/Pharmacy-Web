package com.bush.pharmacy_web_app.service.medicine;

import com.bush.pharmacy_web_app.model.dto.medicine.*;
import com.bush.pharmacy_web_app.repository.medicine.MedicineRepository;
import com.bush.pharmacy_web_app.repository.branch.PharmacyBranchRepository;
import com.bush.pharmacy_web_app.model.dto.warehouse.PharmacyBranchReadDto;
import com.bush.pharmacy_web_app.model.entity.medicine.MedicineImage;
import com.bush.pharmacy_web_app.repository.medicine.filter.MedicineFilter;
import com.bush.pharmacy_web_app.service.medicine.mapper.MedicineCreateMapper;
import com.bush.pharmacy_web_app.service.medicine.mapper.MedicineAdminReadMapper;
import com.bush.pharmacy_web_app.service.medicine.mapper.MedicinePreviewReadMapper;
import com.bush.pharmacy_web_app.service.medicine.mapper.MedicineReadMapper;
import com.bush.pharmacy_web_app.service.order.mapper.PharmacyBranchReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedicineService {
    private final MedicineRepository medicineRepository;
    private final PharmacyBranchRepository branchRepository;

    private final MedicinePreviewReadMapper medicinePreviewReadMapper;
    private final MedicineReadMapper medicineReadMapper;
    private final MedicineCreateMapper medicineCreateMapper;
    private final PharmacyBranchReadMapper branchReadMapper;
    private final MedicineAdminReadMapper adminMedicineReadMapper;

    private final MedicineImageService imageService;

    public List<MedicinePreviewReadDto> findAllPreviews() {
        return medicineRepository.findAll().stream()
                .map(medicinePreviewReadMapper::map)
                .toList();
    }

    public Page<MedicinePreviewReadDto> findAllPreviews(MedicineFilter filter, Pageable pageable) {
        return medicineRepository.findAllByFilter(filter, pageable)
                .map(medicinePreviewReadMapper::map);
    }

    public List<MedicineManufacturerDto> findAllManufacturers() {
        return medicineRepository.findDistinctMedicineManufacturer().stream()
                .map(manufacturer -> new MedicineManufacturerDto(manufacturer.getName()))
                .toList();
    }

    public Optional<MedicineReadDto> findMedicineById(Long id) {
        return medicineRepository.findById(id)
                .map(medicineReadMapper::map);
    }

    public Optional<MedicineAdminReadDto> findAdminDtoById(Long id) {
        return medicineRepository.findById(id)
                .map(adminMedicineReadMapper::map);
    }

    public List<MedicinePreviewReadDto> findByContainingName(String name) {
        return medicineRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(medicinePreviewReadMapper::map)
                .toList();
    }

    public List<PharmacyBranchReadDto> findBranchesMedicineLocated(Long medicineId) {
        return branchRepository.findBranchesWithMedicineLocated(medicineId)
                .stream()
                .map(branchReadMapper::map)
                .toList();
    }

    @Transactional
    public Optional<MedicinePreviewReadDto> createMedicine(MedicineCreateDto createDto, List<MultipartFile> images) {
        return Optional.ofNullable(createDto)
                .map(dto -> {
                    var mergedDto = MedicineCreateDto.IMAGES_STRATEGY_MERGE
                            .merge(MedicineCreateDto.builder().images(images).build(), createDto);
                    return medicineCreateMapper.map(mergedDto);
                })
                .map(medicineRepository::save)
                .map(medicine -> {
                    TransactionSynchronizationManager.registerSynchronization(
                            new TransactionSynchronization() {
                                @Override
                                public void afterCompletion(int status) {
                                    if (status == STATUS_COMMITTED)
                                        Optional.ofNullable(images)
                                                .ifPresent(image -> image
                                                        .stream()
                                                        .filter(Predicate.not(MultipartFile::isEmpty))
                                                        .forEach(file -> imageService.createImage(file,
                                                                String.format("medicine/%d", medicine.getId()))));
                                }
                            }
                    );
                    return medicinePreviewReadMapper.map(medicine);
                });
    }
    @Transactional
    public Optional<MedicinePreviewReadDto> updateMedicine(Long id, MedicineCreateDto createDto, List<MultipartFile> images) {
        return medicineRepository.findById(id)
                .map(lamb -> {
                    var mergedDto = MedicineCreateDto.IMAGES_STRATEGY_MERGE
                            .merge(MedicineCreateDto.builder().images(images).build(), createDto);
                    return medicineCreateMapper.map(mergedDto, lamb);
                })
                .map(medicineRepository::saveAndFlush)
                .map(medicine -> {
                    TransactionSynchronizationManager.registerSynchronization(
                            new TransactionSynchronization() {
                                @Override
                                public void afterCompletion(int status) {
                                    if (status == STATUS_COMMITTED)
                                        Optional.ofNullable(images)
                                                .ifPresent(image -> image
                                                        .stream()
                                                        .filter(Predicate.not(MultipartFile::isEmpty))
                                                        .forEach(file -> imageService.createImage(file,
                                                                String.format("medicine/%d/", medicine.getId()))));
                                }
                            }
                    );
                    return medicinePreviewReadMapper.map(medicine);
                });
    }
    @Transactional
    public boolean deleteMedicine(Long id) {
        return medicineRepository.findById(id)
                .map(medicine -> {
                    var images = medicine
                            .getImage()
                            .stream()
                            .map(MedicineImage::getId)
                            .toList();
                    TransactionSynchronizationManager.registerSynchronization(
                            new TransactionSynchronization() {
                                @Override
                                public void afterCompletion(int status) {
                                    if (status == STATUS_COMMITTED)
                                        images.forEach(imageService::deleteImage);
                                }
                            }
                    );

                    medicineRepository.delete(medicine);
                    return true;
                })
                .orElse(false);
    }
}
