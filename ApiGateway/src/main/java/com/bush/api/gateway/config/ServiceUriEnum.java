package com.bush.api.gateway.config;

public enum ServiceUriEnum {
    PHARMACY_SERVICE("lb://PHARMACY-WEB-APP"), SEARCH_SERVICE("lb://SEARCH-SERVICE");

    private final String uri;

    ServiceUriEnum(String uri) {
        this.uri = uri;
    }

    public String getUri() {
        return uri;
    }
}
