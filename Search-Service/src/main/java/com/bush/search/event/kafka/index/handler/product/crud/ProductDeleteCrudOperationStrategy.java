package com.bush.search.event.kafka.index.handler.product.crud;

import com.bush.search.domain.index.CrudOperationConstants;
import com.bush.search.domain.index.product.ProductPayload;
import com.bush.search.event.kafka.index.handler.strategy.crud.AbstractCrudOperationStrategy;
import com.bush.search.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductDeleteCrudOperationStrategy extends AbstractCrudOperationStrategy<ProductService, ProductPayload> {
    @Autowired
    private ProductService productService;

    public ProductDeleteCrudOperationStrategy() {
        super(CrudOperationConstants.DELETE, ProductService.class, ProductPayload.class);
    }

    @Override
    protected void processInternal(ProductPayload payload) {
        productService.deleteProduct(payload.id().toString());
    }
}
