package com.bush.search.domain.index.product;

import com.bush.search.domain.index.manufacturer.ManufacturerPayload;
import com.bush.search.domain.index.supplier.SupplierPayload;

import java.math.BigDecimal;
import java.util.List;

public record ProductPayload(Long id,
                             String name,
                             List<ProductTypeMappingPayload> type,
                             ManufacturerPayload manufacturer,
                             SupplierPayload supplier,
                             List<Long> image,
                             BigDecimal price,
                             Boolean recipe,
                             String activeIngredient,
                             String expirationDate) {
}
