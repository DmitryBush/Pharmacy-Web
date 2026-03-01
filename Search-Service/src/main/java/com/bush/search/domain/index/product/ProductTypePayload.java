package com.bush.search.domain.index.product;

public record ProductTypePayload(Integer id,
                                 String name,
                                 String slug,
                                 ProductTypeMappingPayload parent) {
}
