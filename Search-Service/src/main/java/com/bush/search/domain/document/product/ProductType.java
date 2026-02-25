package com.bush.search.domain.document.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "product-type")
public class ProductType {
    @Id
    private String id;
    @Field(type = FieldType.Integer)
    private Integer typeId;
    @MultiField(mainField = @Field(type = FieldType.Text, analyzer = "russian"),
            otherFields = @InnerField(suffix = "keyword", type = FieldType.Keyword))
    private String typeName;
    @Field(type = FieldType.Boolean)
    private Boolean isMain;
}
