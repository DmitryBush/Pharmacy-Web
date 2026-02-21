package com.bush.search.domain.index.product;

public record ProductTypeMappingPayload(Integer id,
                                        String type,
                                        ProductTypeMappingPayload parent,
                                        Boolean isMain) {
}
