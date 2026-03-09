package com.bush.search.domain.index.supplier;

import com.bush.search.domain.index.address.AddressPayload;

public record SupplierPayload(String itn,
                              String name,
                              AddressPayload address) {
}
