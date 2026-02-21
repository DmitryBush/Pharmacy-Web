package com.bush.search.domain.document.manufacturer;

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
@Document(indexName = "manufacturer")
public class Manufacturer {
    @Id
    private String id;
    @Field(type = FieldType.Long)
    private Long manufacturerId;
    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Nested)
    private Country country;
}
