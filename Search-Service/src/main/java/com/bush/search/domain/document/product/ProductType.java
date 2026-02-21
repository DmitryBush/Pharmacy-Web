package com.bush.search.domain.document.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "product-type")
public class ProductType {
    @Id
    private String id;
    @Field(type = FieldType.Integer)
    private Integer typeId;
    @Field(type = FieldType.Keyword)
    private String typeName;
    @Field(type = FieldType.Nested)
    private ProductType parent;
    @Field(type = FieldType.Boolean)
    private Boolean isMain;
}
