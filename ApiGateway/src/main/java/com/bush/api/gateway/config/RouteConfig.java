package com.bush.api.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder) {
        RouteLocatorBuilder.Builder builder = getPharmacyServiceRoutes(routeLocatorBuilder.routes());
        builder = getSearchServiceRoutes(builder);
        return builder.build();
    }

    private RouteLocatorBuilder.Builder getPharmacyServiceRoutes(RouteLocatorBuilder.Builder builder) {
        return builder
                .route("mainHttp", predicateSpec -> predicateSpec
                        .path("/")
                        .uri(ServiceUriEnum.PHARMACY_SERVICE.getUri()))
                .route("catalogHttp", predicateSpec -> predicateSpec
                        .path("/catalog/**")
                        .uri(ServiceUriEnum.PHARMACY_SERVICE.getUri()))
                .route("loginHttp", predicateSpec -> predicateSpec
                        .path("/login/**")
                        .uri(ServiceUriEnum.PHARMACY_SERVICE.getUri()))
                .route("registerHttp", predicateSpec -> predicateSpec
                        .path("/register/**")
                        .uri(ServiceUriEnum.PHARMACY_SERVICE.getUri()))
                .route("newsHttp", predicateSpec -> predicateSpec
                        .path("/news/**")
                        .uri(ServiceUriEnum.PHARMACY_SERVICE.getUri()))
                .route("cartHttp", predicateSpec -> predicateSpec
                        .path("/cart")
                        .uri(ServiceUriEnum.PHARMACY_SERVICE.getUri()))
                .route("orderHttp", predicateSpec -> predicateSpec
                        .path("/orders")
                        .uri(ServiceUriEnum.PHARMACY_SERVICE.getUri()))
                .route("adminHttp", predicateSpec -> predicateSpec
                        .path("/admin/**")
                        .uri(ServiceUriEnum.PHARMACY_SERVICE.getUri()))
                .route("catalog", predicateSpec -> predicateSpec
                        .path("/api/*/catalog/**")
                        .uri(ServiceUriEnum.PHARMACY_SERVICE.getUri()))
                .route("branch", predicateSpec -> predicateSpec
                        .path("/api/*/branches/**")
                        .uri(ServiceUriEnum.PHARMACY_SERVICE.getUri()))
                .route("receipt", predicateSpec -> predicateSpec
                        .path("/api/*/receipts/**")
                        .uri(ServiceUriEnum.PHARMACY_SERVICE.getUri()))
                .route("sale", predicateSpec -> predicateSpec
                        .path("/api/*/sales/**")
                        .uri(ServiceUriEnum.PHARMACY_SERVICE.getUri()))
                .route("warehouse", predicateSpec -> predicateSpec
                        .path("/api/*/warehouse/**")
                        .uri(ServiceUriEnum.PHARMACY_SERVICE.getUri()))
                .route("product-image", predicateSpec -> predicateSpec
                        .path("/api/*/product-image/**")
                        .uri(ServiceUriEnum.PHARMACY_SERVICE.getUri()))
                .route("product", predicateSpec -> predicateSpec
                        .path("/api/*/products/**")
                        .uri(ServiceUriEnum.PHARMACY_SERVICE.getUri()))
                .route("news", predicateSpec -> predicateSpec
                        .path("/api/*/news/**")
                        .uri(ServiceUriEnum.PHARMACY_SERVICE.getUri()))
                .route("news-image", predicateSpec -> predicateSpec
                        .path("/api/*/news-images/**")
                        .uri(ServiceUriEnum.PHARMACY_SERVICE.getUri()))
                .route("icon", predicateSpec -> predicateSpec
                        .path("/api/*/icons/**")
                        .uri(ServiceUriEnum.PHARMACY_SERVICE.getUri()))
                .route("product-admin", predicateSpec -> predicateSpec
                        .path("/api/*/admin/products/**")
                        .uri(ServiceUriEnum.PHARMACY_SERVICE.getUri()))
                .route("category-admin", predicateSpec -> predicateSpec
                        .path("/api/*/admin/categories/**")
                        .uri(ServiceUriEnum.PHARMACY_SERVICE.getUri()))
                .route("order-management", predicateSpec -> predicateSpec
                        .path("/api/*/admin/management/orders/**")
                        .uri(ServiceUriEnum.PHARMACY_SERVICE.getUri()))
                // TODO Delegate endpoints to the search service
                .route("country-search", predicateSpec -> predicateSpec
                        .path("/api/*/search/country/**")
                        .uri(ServiceUriEnum.PHARMACY_SERVICE.getUri()))
                .route("manufacturer-search", predicateSpec -> predicateSpec
                        .path("/api/v1/search/manufacturer")
                        .uri(ServiceUriEnum.PHARMACY_SERVICE.getUri()))
                .route("supplier-search", predicateSpec -> predicateSpec
                        .path("/api/*/search/supplier/**")
                        .uri(ServiceUriEnum.PHARMACY_SERVICE.getUri()))
                .route("type-search", predicateSpec -> predicateSpec
                        .path("/api/*/search/type/**")
                        .uri(ServiceUriEnum.PHARMACY_SERVICE.getUri()))
                .route("css", predicateSpec -> predicateSpec
                        .path("/css/**")
                        .uri(ServiceUriEnum.PHARMACY_SERVICE.getUri()))
                .route("js", predicateSpec -> predicateSpec
                        .path("/js/**")
                        .uri(ServiceUriEnum.PHARMACY_SERVICE.getUri()));
    }

    private RouteLocatorBuilder.Builder getSearchServiceRoutes(RouteLocatorBuilder.Builder builder) {
        return builder
                .route("product-search", predicateSpec -> predicateSpec
                        .path("/api/*/search/products/**")
                        .uri(ServiceUriEnum.SEARCH_SERVICE.getUri()));
    }
}
