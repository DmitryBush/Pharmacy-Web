package com.bush.pharmacy_web_app.repository.mapper.strategy;

@FunctionalInterface
public interface MergeStrategy<T> {
    T merge(T from, T to);
}
