package com.bush.search.domain.document.supplier;

import com.bush.search.domain.document.address.Address;
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
@Document(indexName = "supplier")
public class Supplier {
    @Id
    private String itn;
    @Field(type = FieldType.Keyword)
    private String name;
    @Field(type = FieldType.Nested)
    private Address address;
}
