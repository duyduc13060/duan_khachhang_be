package com.example.du_an_demo_be.service.elasticsearch;

import com.example.du_an_demo_be.model.dto.ElasticSearchDto;
import com.example.du_an_demo_be.model.dto.UserDto;
import com.example.du_an_demo_be.model.entity.VectorEntity;
import com.example.du_an_demo_be.payload.request.SearchDTO;
import com.example.du_an_demo_be.payload.response.ServiceResult;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;

import java.util.List;

public interface VectorSearchService {
    List<IndexedObjectInformation> createProductIndexBulk
            (String content);

    List<String> createProductIndex(String content, String fileName, String documentGroup);

    List<String> fetchSuggestions(String query);

    List<VectorEntity> processSearch(String query);

    void deleteAllIndex();

    ServiceResult<Page<VectorEntity>> getAllDocumentInfor(SearchDTO<ElasticSearchDto> searchDTO);

    String getFileContent(String fileName);

    void deleteByFileName(String fileName);
}
