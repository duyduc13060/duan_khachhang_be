package com.example.du_an_demo_be.service.elasticsearch;

import com.example.du_an_demo_be.model.entity.VectorEntity;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;

import java.util.List;

public interface VectorSearchService {
    List<IndexedObjectInformation> createProductIndexBulk
            (List<VectorEntity> vectorEntities);

    String createProductIndex(VectorEntity vectorEntities);

    List<String> fetchSuggestions(String query);

    List<VectorEntity> processSearch(String query);
}
