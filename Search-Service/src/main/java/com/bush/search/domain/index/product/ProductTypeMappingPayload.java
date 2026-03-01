package com.bush.search.domain.index.product;

public record ProductTypeMappingPayload(Integer id,
                                        String type,
                                        String slug,
                                        Boolean isMain) {
}
