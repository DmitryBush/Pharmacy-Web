package com.bush.search.domain.document.address;

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
@Document(indexName = "address")
public class Address {
    @Id
    private String id;
    @Field(type = FieldType.Long)
    private Long addressId;
    @Field(type = FieldType.Keyword)
    private String subject;
    @Field(type = FieldType.Keyword)
    private String district;
    @Field(type = FieldType.Keyword)
    private String settlement;
    @Field(type = FieldType.Keyword)
    private String street;
    @Field(type = FieldType.Keyword)
    private String house;
    @Field(type = FieldType.Keyword)
    private String apartment;
    @Field(type = FieldType.Keyword)
    private String postalCode;
}
