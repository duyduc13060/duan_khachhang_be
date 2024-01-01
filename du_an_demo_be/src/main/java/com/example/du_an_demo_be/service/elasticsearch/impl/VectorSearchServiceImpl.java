package com.example.du_an_demo_be.service.elasticsearch.impl;

import com.example.du_an_demo_be.model.entity.VectorEntity;
import com.example.du_an_demo_be.repository.elasticsearch.ElasticsearchVectorRepository;
import com.example.du_an_demo_be.service.elasticsearch.VectorSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VectorSearchServiceImpl implements VectorSearchService {

    private final ElasticsearchVectorRepository elasticsearchVectorRepository;
    private final ElasticsearchOperations elasticsearchOperations;
    private static final String MESSAGE_INDEX = "message_index";

    @Override
    public List<IndexedObjectInformation> createProductIndexBulk
            (final List<VectorEntity> vectorEntities) {

        List<IndexQuery> queries = vectorEntities.stream()
                .map(product->
                        new IndexQueryBuilder()
                                .withId(product.getId().toString())
                                .withObject(product).build())
                .collect(Collectors.toList());;

        return elasticsearchOperations.bulkIndex(queries, IndexCoordinates.of(MESSAGE_INDEX));
    }

    @Override
    public String createProductIndex(VectorEntity vectorEntities) {

        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(vectorEntities.getId())
                .withObject(vectorEntities).build();

        String documentId = elasticsearchOperations
                .index(indexQuery, IndexCoordinates.of(MESSAGE_INDEX));

        return documentId;
    }


    private List<String> splitIntoTrunks1(String doc, int trunkSize) {
        // Cắt đoạn văn bản thành các đoạn nhỏ (trunks) với độ dài trunkSize
        List<String> trunks = new ArrayList<>();
        for (int i = 0; i < doc.length(); i += trunkSize) {
            int end = Math.min(i + trunkSize, doc.length());
            trunks.add(doc.substring(i, end));
        }
        return trunks;
    }


}
