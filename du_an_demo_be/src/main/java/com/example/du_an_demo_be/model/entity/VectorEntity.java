package com.example.du_an_demo_be.model.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import java.util.List;

@Document(indexName = "message_index")
@Data
public class VectorEntity {

    @Id
    private String id;

    @Field(type = FieldType.Text, name = "content")
    private String content;

    @Field(type = FieldType.Text, name = "full_content")
    private String fullContent;

    @Field(type = FieldType.Text, name = "file_name")
    private String fileName;

    @Field(type = FieldType.Integer, name = "trunk_count")
    private int trunkCount;

    @Field(type = FieldType.Text, name = "creator")
    private String creator;

    @Field(type = FieldType.Text, name = "document_group")
    private String documentGroup;

    public VectorEntity() {
    }

    public VectorEntity(String fileName, int trunkCount) {
        this.fileName = fileName;
        this.trunkCount = trunkCount;
    }
}
