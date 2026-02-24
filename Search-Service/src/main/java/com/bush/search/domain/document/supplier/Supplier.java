package com.bush.search.domain.document.supplier;

import com.bush.search.domain.document.address.Address;
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
@Document(indexName = "supplier")
public class Supplier {
    @Id
    private String itn;
    @MultiField(mainField = @Field(type = FieldType.Text, analyzer = "russian"),
            otherFields = @InnerField(suffix = "keyword", type = FieldType.Keyword))
    private String name;
    @Field(type = FieldType.Nested)
    private Address address;
}
