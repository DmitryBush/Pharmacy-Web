package com.bush.pharmacy_web_app.service.medicine;

import com.bush.pharmacy_web_app.repository.medicine.MedicineImageRepository;
import com.bush.pharmacy_web_app.model.entity.medicine.ProductImage;
import com.bush.pharmacy_web_app.service.filesystem.FileSystemStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
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
                .map(ProductImage::getPath)
                .toList();
    }

    private List<String> findProductImageListByMedicineId(Long id) {
        return imageRepository.findByProductId(id)
                .stream()
                .map(ProductImage::getPath)
                .toList();
    }

    private Optional<String> findImageByMedicineIdAndPath(Long id, String filename) {
        return imageRepository.findByProductIdAndPath(id, filename)
                .map(ProductImage::getPath);
    }

    public Optional<Resource> findImageById(Long id) {
        return imageRepository.findById(id)
                .map(image ->
                        storageService.loadAsResource(String.format("product/%d", image.getProduct().getId()),
                                image.getPath()));
    }

    public void createImage(MultipartFile file, String path) {
        storageService.store(file, path);
    }

    @Transactional
    public boolean deleteImage(Long id) {
        return imageRepository.findById(id)
                .map(medicineImage -> {
                    imageRepository.delete(medicineImage);

                    TransactionSynchronizationManager.registerSynchronization(
                            new TransactionSynchronization() {
                                @Override
                                public void afterCommit() {
                                    storageService.delete(String.format("product/%d/%s",
                                            medicineImage.getProduct().getId(), medicineImage.getPath()));
                                }
                            }
                    );
                    return true;
                })
                .orElse(false);
    }

    public Optional<Resource> findProductImageByIdAndName(Long id, String filename) {
        return findImageByMedicineIdAndPath(id, filename)
                .map(path -> storageService.loadAsResource(String.format("product/%d", id), path));
    }
}
