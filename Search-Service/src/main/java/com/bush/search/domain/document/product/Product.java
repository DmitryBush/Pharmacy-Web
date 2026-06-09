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
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "product")
@Setting(settingPath = "elasticsearch/es-settings.json")
public class Product {
    @Id
    private String id;
    @Field(type = FieldType.Long)
    private Long productId;
    @MultiField(
            mainField = @Field(type = FieldType.Text, analyzer = "russian"),
            otherFields = {
                    @InnerField(suffix = "suggest", type = FieldType.Text,
                            analyzer = "russian_ngram", searchAnalyzer = "russian"),
                    @InnerField(suffix = "keyword", type = FieldType.Keyword)
            }
    )
    private String name;
    @Field(type = FieldType.Nested, store = true)
    private List<ProductType> type;
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

    @Field(type = FieldType.Nested, store = true)
    private Supplier supplier;
    @Field(type = FieldType.Nested, store = true)
    private Manufacturer manufacturer;
}
