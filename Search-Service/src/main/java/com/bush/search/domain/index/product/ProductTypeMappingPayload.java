package com.bush.search.domain.index.product;

public record ProductTypeMappingPayload(ProductPayload product,
                                        ProductTypePayload type,
                                        Boolean isMain) {
}
