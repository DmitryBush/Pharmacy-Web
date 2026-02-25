package com.bush.search.domain.index.product;

public record ProductTypePayload(Integer id,
                                 String type,
                                 ProductTypeMappingPayload parent) {
}
