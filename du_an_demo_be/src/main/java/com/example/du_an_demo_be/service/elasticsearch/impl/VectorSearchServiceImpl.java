package com.example.du_an_demo_be.service.elasticsearch.impl;

import com.example.du_an_demo_be.model.entity.VectorEntity;
import com.example.du_an_demo_be.repository.elasticsearch.ElasticsearchVectorRepository;
import com.example.du_an_demo_be.service.elasticsearch.VectorSearchService;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VectorSearchServiceImpl implements VectorSearchService {

    private final ElasticsearchVectorRepository elasticsearchVectorRepository;
    private final ElasticsearchOperations elasticsearchOperations;
    private static final String MESSAGE_INDEX = "message_index";
    private static final int TRUNK_SIZE = 500;

    @Override
    public List<IndexedObjectInformation> createProductIndexBulk
            (final String content) {

        List<String> trunks = this.splitIntoTrunks1(content, TRUNK_SIZE);
        List<VectorEntity> vectorEntities = new ArrayList<>();

        trunks.forEach(item ->{
            VectorEntity vector = new VectorEntity();
            vector.setId("vector_" +
                    Calendar.getInstance().get(Calendar.YEAR) +
                    (Calendar.getInstance().get(Calendar.MONTH) + 1) +
                    Calendar.getInstance().get(Calendar.DATE) +
                    "_" +
                    Calendar.getInstance().get(Calendar.HOUR) +
                    Calendar.getInstance().get(Calendar.MINUTE) +
                    Calendar.getInstance().get(Calendar.MILLISECOND));
            vector.setDocument(content);

            vectorEntities.add(vector);
        });

        List<IndexQuery> queries = vectorEntities.stream()
                .map(product->
                        new IndexQueryBuilder()
                                .withId(product.getId())
                                .withObject(product).build())
                .collect(Collectors.toList());;

        return elasticsearchOperations.bulkIndex(queries, IndexCoordinates.of(MESSAGE_INDEX));
    }

    @Override
    public List<String> createProductIndex(String content) {
        List<String> trunks = this.splitIntoTrunks1(content, TRUNK_SIZE);
        List<String> documentIds = new ArrayList<>();

        trunks.forEach(item ->{
            VectorEntity vectorEntities = new VectorEntity();
            vectorEntities.setId("vector_" +
                    Calendar.getInstance().get(Calendar.YEAR) +
                    (Calendar.getInstance().get(Calendar.MONTH) + 1) +
                    Calendar.getInstance().get(Calendar.DATE) +
                    "_" +
                    Calendar.getInstance().get(Calendar.HOUR) +
                    Calendar.getInstance().get(Calendar.MINUTE) +
                    Calendar.getInstance().get(Calendar.MILLISECOND));
            vectorEntities.setDocument(item);

            IndexQuery indexQuery = new IndexQueryBuilder()
                    .withId(vectorEntities.getId())
                    .withObject(vectorEntities).build();

            String documentId = elasticsearchOperations
                    .index(indexQuery, IndexCoordinates.of(MESSAGE_INDEX));

            documentIds.add(documentId);

        });
        return documentIds;
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


    @Override
    public List<String> fetchSuggestions(String query) {
        QueryBuilder queryBuilder = QueryBuilders
                .wildcardQuery("document", query + "*");

        Query searchQuery = new NativeSearchQueryBuilder()
                .withFilter(queryBuilder)
                .withPageable(PageRequest.of(0, 5))
                .build();

        SearchHits<VectorEntity> searchSuggestions =
                elasticsearchOperations.search(searchQuery,
                        VectorEntity.class,
                        IndexCoordinates.of(MESSAGE_INDEX));

        List<String> suggestions = new ArrayList<String>();

        searchSuggestions.getSearchHits().forEach(searchHit->{
            suggestions.add(searchHit.getContent().getDocument());
        });
        return suggestions;
    }


    @Override
    public List<VectorEntity> processSearch(final String query) {
        // 1. Create query on multiple fields enabling fuzzy search
        QueryBuilder queryBuilder =
                QueryBuilders
                        .multiMatchQuery(query, "document", "description")
                        .fuzziness(Fuzziness.AUTO);

        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withSort(SortBuilders.scoreSort().order(SortOrder.DESC))
                .build();

        // 2. Execute search
        SearchHits<VectorEntity> productHits =
                elasticsearchOperations
                        .search(searchQuery, VectorEntity.class,
                                IndexCoordinates.of(MESSAGE_INDEX));

        // 3. Map searchHits to product list
        List<VectorEntity> productMatches = new ArrayList<VectorEntity>();
        int i = 0;
        for (SearchHit<VectorEntity> searchHit : productHits) {
            productMatches.add(searchHit.getContent());
            if (productMatches.size() > 10) {
                break;
            }
        }
        return productMatches;
    }

    @Override
    public List<VectorEntity> searchPassageRetrieval(final String query) {
        // 1. Tạo truy vấn tìm kiếm các đoạn văn phù hợp với truy vấn
        QueryBuilder queryBuilder = QueryBuilders
                .multiMatchQuery(query, "document", "description")
                .fuzziness(Fuzziness.AUTO);

        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withSort(SortBuilders.scoreSort().order(SortOrder.DESC))
                .build();

        // 2. Thực thi truy vấn tìm kiếm
        SearchHits<VectorEntity> searchHits =
                elasticsearchOperations
                        .search(searchQuery, VectorEntity.class,
                                IndexCoordinates.of(MESSAGE_INDEX));

        // 3. Chuyển đổi SearchHits thành danh sách các đoạn văn
        List<VectorEntity> passages = new ArrayList<>();
        int i = 0;
        for (SearchHit<VectorEntity> searchHit : searchHits) {
            passages.add(searchHit.getContent());
            if (passages.size() > 10) {
                break;
            }
        }

        return passages;
    }

    public void deleteDocumentIndex() {
        try {
            IndexOperations indexOperations = elasticsearchOperations.indexOps(IndexCoordinates.of(MESSAGE_INDEX));
            indexOperations.delete();
            System.out.println("Ducuments index deleted!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
