package com.example.du_an_demo_be.model.entity;


import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;


@Document(indexName = "trunk_index")
@Data
public class TrunkEntity {

    @Id
    private String id;

    @Field(type = FieldType.Integer, name = "trunkContent")
    @Lob
    private String trunkContent;

}
