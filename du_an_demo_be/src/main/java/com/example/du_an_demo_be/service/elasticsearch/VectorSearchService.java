package com.example.du_an_demo_be.service.elasticsearch;

import com.example.du_an_demo_be.model.entity.VectorEntity;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;

import java.util.List;

public interface VectorSearchService {
    List<IndexedObjectInformation> createProductIndexBulk
            (String content);

    List<String> createProductIndex(String content, String fileName);

    List<String> fetchSuggestions(String query);

    List<VectorEntity> processSearch(String query);

    void deleteDocumentIndex();

    List<VectorEntity> searchPassageRetrieval(final String query);

    String getFileContent(String fileName);
}
