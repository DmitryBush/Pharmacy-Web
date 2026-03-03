package com.bush.pharmacy_web_app.service.medicine.mapper;

import com.bush.pharmacy_web_app.model.entity.medicine.Product;
import com.bush.pharmacy_web_app.model.entity.medicine.ProductImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductImageCreateMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "path", source = "product.id", qualifiedByName = "mapPath")
    @Mapping(target = "product", source = "product")
    ProductImage mapToProductImage(Product product);

    @Named("mapPath")
    default String mapPath(final Long productId) {
        final UUID pathUuidId = UUID.nameUUIDFromBytes(productId.toString().getBytes());
        final UUID imageUuidId = UUID.randomUUID();
        return String.format("product/%s/%s", pathUuidId, imageUuidId);
    }
}
