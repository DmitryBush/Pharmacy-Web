package com.bush.search.domain.index.address;

public record AddressPayload(Long id,
                             String subject,
                             String district,
                             String settlement,
                             String street,
                             String house,
                             String apartment,
                             String postalCode) {
}
