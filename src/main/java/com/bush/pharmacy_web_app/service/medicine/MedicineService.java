package com.bush.pharmacy_web_app.service.medicine;

import com.bush.outbox.domain.dto.OutboxRecordDto;
import com.bush.outbox.domain.entity.CrudOperationType;
import com.bush.outbox.service.OutboxService;
import com.bush.pharmacy_web_app.model.dto.medicine.MedicineAdminReadDto;
import com.bush.pharmacy_web_app.model.dto.medicine.MedicinePreviewReadDto;
import com.bush.pharmacy_web_app.model.dto.medicine.MedicineReadDto;
import com.bush.pharmacy_web_app.model.dto.medicine.ProductCreateDto;
import com.bush.pharmacy_web_app.model.dto.warehouse.PharmacyBranchReadDto;
import com.bush.pharmacy_web_app.model.entity.Supplier;
import com.bush.pharmacy_web_app.model.entity.manufacturer.Manufacturer;
import com.bush.pharmacy_web_app.model.entity.medicine.MedicineType;
import com.bush.pharmacy_web_app.model.entity.medicine.Product;
import com.bush.pharmacy_web_app.model.entity.medicine.ProductImage;
import com.bush.pharmacy_web_app.repository.branch.PharmacyBranchRepository;
import com.bush.pharmacy_web_app.repository.medicine.MedicineRepository;
import com.bush.pharmacy_web_app.repository.medicine.filter.MedicineFilter;
import com.bush.pharmacy_web_app.service.branch.mapper.PharmacyBranchReadMapper;
import com.bush.pharmacy_web_app.service.manufacturer.ManufacturerService;
import com.bush.pharmacy_web_app.service.medicine.mapper.ProductCreateMapper;
import com.bush.pharmacy_web_app.service.medicine.mapper.ProductReadMapper;
import com.bush.pharmacy_web_app.service.supplier.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MedicineService {
    private final MedicineRepository productRepository;
    private final PharmacyBranchRepository branchRepository;

    private final ProductCreateMapper productCreateMapper;
    private final ProductReadMapper productReadMapper;
    private final PharmacyBranchReadMapper pharmacyBranchReadMapper;

    private final SupplierService supplierService;
    private final ManufacturerService manufacturerService;
    private final ProductTypeService productTypeService;
    private final ProductTypeMappingService typeMappingService;
    private final ProductImageService imageService;
    private final OutboxService outboxService;

    public List<MedicinePreviewReadDto> findAllPreviews() {
        return productRepository.findAll().stream()
                .map(productReadMapper::mapToMedicinePreviewReadDto)
                .toList();
    }

    public Page<MedicinePreviewReadDto> findAllPreviews(MedicineFilter filter, Pageable pageable) {
        return productRepository.findAllByFilter(filter, pageable)
                .map(productReadMapper::mapToMedicinePreviewReadDto);
    }

    public List<MedicinePreviewReadDto> findRandomMedicine(Integer count) {
        return productRepository.findRandomMedicine(count).stream()
                .map(productReadMapper::mapToMedicinePreviewReadDto)
                .toList();
    }

    public Optional<MedicineReadDto> findMedicineById(Long id) {
        return productRepository.findById(id)
                .map(productReadMapper::mapToMedicineReadDto);
    }

    public Optional<MedicineAdminReadDto> findAdminDtoById(Long id) {
        return productRepository.findById(id)
                .map(productReadMapper::mapToMedicineAdminReadDto);
    }

    public List<MedicinePreviewReadDto> findByContainingName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(productReadMapper::mapToMedicinePreviewReadDto)
                .toList();
    }

    public List<PharmacyBranchReadDto> findBranchesMedicineLocated(Long medicineId) {
        return branchRepository.findBranchesWithMedicineLocated(medicineId)
                .stream()
                .map(pharmacyBranchReadMapper::mapToPharmacyBranchReadDto)
                .toList();
    }

    @Transactional
    public MedicinePreviewReadDto createMedicine(ProductCreateDto createDto, List<MultipartFile> images) {
        Supplier supplier = supplierService.getReferenceById(supplierService
                .findOrCreateSupplier(createDto.supplier()).itn());
        Manufacturer manufacturer = manufacturerService.getReferenceById(manufacturerService
                .findOrCreateManufacturer(createDto.manufacturer()).id());
        Product product = Optional.of(createDto)
                .map(dto -> productCreateMapper.mapToProduct(dto, supplier, manufacturer))
                .map(productRepository::save)
                .map(savedProduct -> outboxService.createRecord(
                        new OutboxRecordDto<>("product", CrudOperationType.C, savedProduct)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        try {
            Optional.ofNullable(images).ifPresent(imageList -> images
                    .forEach(file -> imageService.createImage(file, product)));
            createDto.type().forEach(type -> {
                MedicineType medicineType = productTypeService.getReferenceById(
                        productTypeService.findByTypeName(type.type()).id());
                typeMappingService.createProductTypeMapping(product, medicineType, type.isMain());
            });
            return productReadMapper.mapToMedicinePreviewReadDto(product);
        } catch (Exception e) {
            log.error("Caught exception while saving product - {}", e.getMessage());
            product.getImage().forEach(imageService::deleteImage);
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public MedicinePreviewReadDto updateMedicine(Long id, ProductCreateDto createDto, List<MultipartFile> images) {
        Supplier supplier = supplierService.getReferenceById(supplierService
                .findOrCreateSupplier(createDto.supplier()).itn());
        Manufacturer manufacturer = manufacturerService.getReferenceById(manufacturerService
                .findOrCreateManufacturer(createDto.manufacturer()).id());
        Product product = Optional.of(id)
                .flatMap(productRepository::findById)
                .map(findedProduct -> productCreateMapper.mapToProduct(createDto, supplier, manufacturer))
                .map(productRepository::saveAndFlush)
                .map(updatedProduct -> outboxService.createRecord(
                        new OutboxRecordDto<>("product", CrudOperationType.U, updatedProduct)))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<ProductImage> productImages = new ArrayList<>();
        try {
            Optional.ofNullable(images).ifPresent(imageList -> images
                    .forEach(file -> productImages.add(imageService.createImage(file, product))));
            createDto.type().forEach(type -> {
                MedicineType medicineType = productTypeService.getReferenceById(
                        productTypeService.findByTypeName(type.type()).id());
                typeMappingService.createProductTypeMapping(product, medicineType, type.isMain());
            });
            return productReadMapper.mapToMedicinePreviewReadDto(product);
        } catch (Exception e) {
            log.error("Caught exception while updating product - {}", e.getMessage());
            productImages.forEach(imageService::deleteImage);
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public boolean deleteMedicine(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    productRepository.delete(product);
                    product.getImage().forEach(productImage ->
                            imageService.deleteImage(productImage.getId()));
                    outboxService.createRecord(new OutboxRecordDto<>("product", CrudOperationType.D, product));
                    return true;
                })
                .orElse(false);
    }
}
