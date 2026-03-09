package com.bush.pharmacy_web_app.service.storage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum BucketConstantEnum {
    PRODUCT("product"), NEWS("news");

    private final String bucket;
}
