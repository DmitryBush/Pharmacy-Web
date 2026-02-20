package com.bush.search.domain.index.product;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ProductTypeMappingPayload(@JsonProperty("type.id.type") ProductTypePayload type,
                                        Boolean isMain) {
}
