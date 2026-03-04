package com.bush.pharmacy_web_app.it.service.product;

import com.bush.pharmacy_web_app.model.dto.address.AddressCreateDto;
import com.bush.pharmacy_web_app.model.dto.manufacturer.CountryCreateDto;
import com.bush.pharmacy_web_app.model.dto.manufacturer.ManufacturerCreateDto;
import com.bush.pharmacy_web_app.model.dto.product.ProductCreateDto;
import com.bush.pharmacy_web_app.model.dto.product.ProductPreviewReadDto;
import com.bush.pharmacy_web_app.model.dto.product.ProductTypeDto;
import com.bush.pharmacy_web_app.model.dto.product.ProductTypeMappingDto;
import com.bush.pharmacy_web_app.model.dto.supplier.SupplierCreateDto;
import com.bush.pharmacy_web_app.service.product.ProductService;
import com.bush.pharmacy_web_app.service.product.ProductTypeService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.testcontainers.containers.MinIOContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

@ActiveProfiles("test")
@Testcontainers
@SpringBootTest
public class ProductServiceIT {
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.10-alpine");
    @Container
    static MinIOContainer minIOContainer = new MinIOContainer("minio/minio:RELEASE.2025-09-07T16-13-09Z")
            .withUserName("minioadmin")
            .withPassword("minioadmin");

    @Autowired
    private ProductService productService;

    @DynamicPropertySource
    static void dynamicProperty(DynamicPropertyRegistry registry) {
        registry.add("storage.object.endpoint", () ->
                "http://127.0.0.1:%s".formatted(minIOContainer.getMappedPort(9000)));
    }

    @BeforeAll
    public static void beforeAll(@Autowired ProductTypeService productTypeService) {
        ProductTypeDto typeDto = new ProductTypeDto(null, "example", "example", null);
        productTypeService.createDto(typeDto);
    }

    @Test
    public void createProduct() {
        ProductPreviewReadDto previewReadDto = productService.createMedicine(getProductCreateDto(), getMultipartFileList());

        Assertions.assertEquals(previewReadDto, productService.findProductPreviewById(previewReadDto.id()));
    }

    private @NotNull ProductCreateDto getProductCreateDto() {
        SupplierCreateDto supplierCreateDto = getSupplier();
        ManufacturerCreateDto manufacturerCreateDto = getManufacturer();
        ProductTypeMappingDto typeMapping = getProductTypeMappingDto();

        return new ProductCreateDto(
                null, "example", List.of(typeMapping), manufacturerCreateDto, new BigDecimal("157.00"),
                false, supplierCreateDto, "example", "long", null,
                null, null, null, null, null, null,
                null, null, null
        );
    }

    private static @NotNull ManufacturerCreateDto getManufacturer() {
        CountryCreateDto countryCreateDto = new CountryCreateDto("Russia");
        return new ManufacturerCreateDto(null, "example", countryCreateDto);
    }

    private static @NotNull SupplierCreateDto getSupplier() {
        AddressCreateDto addressCreateDto = new AddressCreateDto("Московская", "Москва",
                "Проспект Мира", "45", "1", "123456");
        return new SupplierCreateDto("1234567890", "example", addressCreateDto);
    }

    private static @NotNull ProductTypeMappingDto getProductTypeMappingDto() {
        return new ProductTypeMappingDto("example", null, true);
    }

    private static @NotNull List<MultipartFile> getMultipartFileList() {
        return List.of(new MockMultipartFile("fileExample", "fileExample",
                "image/png", new byte[]{1, 0, 1}));
    }

    @Test
    public void updateProduct() {
        ProductPreviewReadDto previewReadDto = productService.createMedicine(getProductCreateDto(), getMultipartFileList());

        previewReadDto = productService.updateMedicine(previewReadDto.id(), getProductCreateDto(), getMultipartFileList());

        Assertions.assertEquals(previewReadDto, productService.findProductPreviewById(previewReadDto.id()));
    }

    @Test
    public void updateNonExistedProduct() {
        Assertions.assertThrows(ResponseStatusException.class,
                () -> productService.updateMedicine(Long.MIN_VALUE, getProductCreateDto(), getMultipartFileList()));
    }

    @Test
    public void deleteProduct() {
        ProductCreateDto productCreateDto = getProductCreateDto();
        ProductPreviewReadDto previewReadDto = productService.createMedicine(productCreateDto, getMultipartFileList());

        Boolean result = productService.deleteMedicine(previewReadDto.id());

        Assertions.assertAll(
                () -> Assertions.assertEquals(true, result),
                () -> Assertions.assertThrows(ResponseStatusException.class, () -> productService
                        .findProductPreviewById(previewReadDto.id()))
        );
    }

    @Test
    public void deleteNotExistedProduct() {
        Assertions.assertFalse(() -> productService.deleteMedicine(Long.MIN_VALUE));
    }
}
