package com.bush.search.domain.index.product;

public record ProductTypeMappingPayload(Integer id,
                                        String name,
                                        String slug,
                                        ProductTypeMappingPayload parent,
                                        Boolean isMain) {
}
