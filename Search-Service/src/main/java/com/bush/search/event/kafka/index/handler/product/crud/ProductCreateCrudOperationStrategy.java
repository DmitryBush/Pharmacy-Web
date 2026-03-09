package com.bush.search.event.kafka.index.handler.product.crud;

import com.bush.search.domain.index.CrudOperationConstants;
import com.bush.search.domain.index.product.ProductPayload;
import com.bush.search.event.kafka.index.handler.strategy.crud.AbstractCrudOperationStrategy;
import com.bush.search.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductCreateCrudOperationStrategy extends AbstractCrudOperationStrategy<ProductService, ProductPayload> {
    @Autowired
    private ProductService productService;

    public ProductCreateCrudOperationStrategy() {
        super(CrudOperationConstants.CREATE, ProductService.class, ProductPayload.class);
    }

    @Override
    protected void processInternal(ProductPayload payload) {
        productService.createProduct(payload);
    }
}
