package com.bush.pharmacy_web_app.shared.mapper;

public interface DtoMapper<F, T> {
    T map(F obj);
    default T map(F fromObj, T toObj) {
        return toObj;
    }
}
