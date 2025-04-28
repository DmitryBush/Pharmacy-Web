package com.bush.pharmacy_web_app.service;

import com.bush.pharmacy_web_app.repository.MedicineImageRepository;
import com.bush.pharmacy_web_app.repository.dto.medicine.MedicineImageReadDto;
import com.bush.pharmacy_web_app.repository.entity.medicine.MedicineImage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedicineImageService {
    private final MedicineImageRepository imageRepository;
    private final FileSystemStorageService storageService;

    public List<String> findProductImageList() {
        return imageRepository.findAll()
                .stream()
                .map(MedicineImage::getPath)
                .toList();
    }

    private List<String> findProductImageListByMedicineId(Long id) {
        return imageRepository.findByMedicineId(id)
                .stream()
                .map(MedicineImage::getPath)
                .toList();
    }

    private Optional<String> findImageByMedicineIdAndPath(Long id, String filename) {
        return imageRepository.findByMedicineIdAndPath(id, filename)
                .map(MedicineImage::getPath);
    }

    public Optional<Resource> findImageById(Long id) {
        return imageRepository.findById(id)
                .map(MedicineImage::getPath)
                .map(storageService::loadAsResource);
    }

    public void createImage(MultipartFile file) {
        storageService.store(file);
    }

    @Transactional
    public boolean deleteImage(Long id) {
        return imageRepository.findById(id)
                .map(medicineImage -> {
                    imageRepository.delete(medicineImage);
                    storageService.delete(medicineImage.getPath());
                    return true;
                })
                .orElse(false);
    }

    public Optional<Resource> findProductImageByIdAndName(Long id, String filename) {
        return findImageByMedicineIdAndPath(id, filename)
                .map(storageService::loadAsResource);
    }
}
