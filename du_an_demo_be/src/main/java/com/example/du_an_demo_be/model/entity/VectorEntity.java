package com.example.du_an_demo_be.model.entity;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vectors")
@Document(indexName = "vector_index")
@Data
public class VectorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jpaId;

    @Field(type = FieldType.Text, name = "document")
    @Lob
    private String document;

    @ElementCollection
    private List<String> vector;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "vector_trunks",
            joinColumns = @JoinColumn(name = "vector_id"),
            inverseJoinColumns = @JoinColumn(name = "trunk_id")
    )
    private List<TrunkEntity> trunks;

}
