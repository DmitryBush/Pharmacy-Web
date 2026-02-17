package com.bush.search.domain.document.product;

import com.bush.search.domain.document.manufacturer.Manufacturer;
import com.bush.search.domain.document.supplier.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "product")
public class Product {
    @Id
    private String id;
    @Field(type = FieldType.Long)
    private Long productId;
    @Field(type = FieldType.Keyword)
    private String name;
    @Field(type = FieldType.Scaled_Float, scalingFactor = 100)
    private BigDecimal price;
    @Field(type = FieldType.Boolean)
    private Boolean recipe;
    @Field(type = FieldType.Keyword)
    private String activeIngredient;
    @Field(type = FieldType.Keyword)
    private String expirationDate;
    @Field(type = FieldType.Long)
    private List<Long> image;

    @Field(type = FieldType.Nested)
    private Supplier supplier;
    @Field(type = FieldType.Nested)
    private Manufacturer manufacturer;
}
