package com.bush.search.domain.index.product;

import java.util.List;

public record ProductTypePayload(Integer id,
                                 String type,
                                 ProductTypePayload parent,
                                 List<ProductTypePayload> childTypes) {
}
