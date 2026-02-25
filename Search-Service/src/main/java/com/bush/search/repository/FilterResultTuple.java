package com.bush.search.repository;

public record FilterResultTuple<R, A>(R filtrationResult, A aggregation) {
}
