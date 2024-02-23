package com.example.du_an_demo_be.service.elasticsearch.impl;

import com.example.du_an_demo_be.model.dto.ElasticSearchDto;
import com.example.du_an_demo_be.model.entity.VectorEntity;
import com.example.du_an_demo_be.payload.request.SearchDTO;
import com.example.du_an_demo_be.payload.response.ServiceResult;
import com.example.du_an_demo_be.repository.elasticsearch.ElasticsearchVectorRepository;
import com.example.du_an_demo_be.security.CustomerDetailService;
import com.example.du_an_demo_be.service.elasticsearch.VectorSearchService;
import com.example.du_an_demo_be.until.CurrentUserUtils;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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
            vector.setContent(content);

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
    public List<String> createProductIndex(String content, String fileName) {
        List<String> trunks = this.splitIntoTrunks1(content, TRUNK_SIZE);
        List<String> documentIds = new ArrayList<>();
        AtomicInteger count = new AtomicInteger(1);
        CustomerDetailService customer = CurrentUserUtils.getCurrentUserUtils();

        for (int i = 0; i < trunks.size(); i++) {
            String item = trunks.get(i);
            VectorEntity vectorEntities = new VectorEntity();
            vectorEntities.setId("vector_" +
                    Calendar.getInstance().get(Calendar.YEAR) +
                    (Calendar.getInstance().get(Calendar.MONTH) + 1) +
                    Calendar.getInstance().get(Calendar.DATE) +
                    "_" +
                    Calendar.getInstance().get(Calendar.HOUR) +
                    Calendar.getInstance().get(Calendar.MINUTE) +
                    Calendar.getInstance().get(Calendar.MILLISECOND));
            vectorEntities.setContent(item);
            vectorEntities.setFileName(fileName);
            vectorEntities.setTrunkCount(count.get());
            vectorEntities.setCreator(customer.getUsername());

            // Điều chỉnh nội dung dựa trên giá trị của count
            if (count.get() == 1) {
                vectorEntities.setFullContent(item);
            } else {
                String prevItem = i > 0 ? trunks.get(i - 1) : "";
                String nextItem = i < trunks.size() - 1 ? trunks.get(i + 1) : "";
                vectorEntities.setFullContent(prevItem + item + nextItem);
            }

            IndexQuery indexQuery = new IndexQueryBuilder()
                    .withId(vectorEntities.getId())
                    .withObject(vectorEntities).build();

            String documentId = elasticsearchOperations
                    .index(indexQuery, IndexCoordinates.of(MESSAGE_INDEX));

            documentIds.add(documentId);
            count.incrementAndGet();
        }
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
                .wildcardQuery("content", query + "*");

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
            suggestions.add(searchHit.getContent().getContent());
        });
        return suggestions;
    }


    @Override
    public List<VectorEntity> processSearch(final String query) {
        // 1. Create query on multiple fields enabling fuzzy search
        QueryBuilder queryBuilder =
                QueryBuilders
                        .multiMatchQuery(query, "content", "description")
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
//        productMatches.add(productHits.getSearchHit(0).getContent());
//        int trunkCount = productHits.getSearchHit(0).getContent().getTrunkCount();
//
//        for (int i = 1; i < productHits.getSearchHits().size(); i++) {
//            VectorEntity vectorEntity = productHits.getSearchHit(i).getContent();
//            if (trunkCount == 1 && vectorEntity.getTrunkCount() == 2) {
//                productMatches.add(vectorEntity);
//                break;
//            } else if (trunkCount > 1 && (vectorEntity.getTrunkCount() == trunkCount - 1 || vectorEntity.getTrunkCount() == trunkCount + 1)) {
//                productMatches.add(vectorEntity);
//                if (productMatches.size() == 3) {
//                    break;
//                }
//            }
//        }

        int i = 0;
        for (SearchHit<VectorEntity> searchHit : productHits) {
            productMatches.add(searchHit.getContent());
            if (productMatches.size() > 9) {
                break;
            }
        }
        return productMatches;
    }

    @Override
    public ServiceResult<Page<VectorEntity>> searchPassageRetrieval(SearchDTO<ElasticSearchDto> searchDTO) {
        // 1. Tạo truy vấn tìm kiếm các đoạn văn phù hợp với truy vấn
        QueryBuilder queryBuilder = QueryBuilders
                .multiMatchQuery(searchDTO.getData().getCreator(),  "creator")
                .fuzziness(Fuzziness.AUTO);

        TermsAggregationBuilder aggregationBuilder = AggregationBuilders
                .terms("group_by_fileName")
                .field("file_name.keyword");

        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .addAggregation(aggregationBuilder)
                .withSort(SortBuilders.scoreSort().order(SortOrder.DESC))
                .build();

        // 2. Thực thi truy vấn tìm kiếm
        SearchHits<VectorEntity> searchHits =
                elasticsearchOperations
                        .search(searchQuery, VectorEntity.class,
                                IndexCoordinates.of(MESSAGE_INDEX));

        Map<String, VectorEntity> resultMap = new HashMap<>();
        for (SearchHit<VectorEntity> searchHit : searchHits) {
            String fileName = searchHit.getContent().getFileName();

            // Kiểm tra xem đã có VectorEntity cho fileName này trong Map chưa
            if (resultMap.containsKey(fileName)) {
                VectorEntity existingEntity = resultMap.get(fileName);
                existingEntity.setContent(existingEntity.getContent() + "\n" + searchHit.getContent().getContent());
                existingEntity.setCreator(searchHit.getContent().getCreator());
            } else {
                // Nếu chưa có, thêm mới VectorEntity vào Map
                VectorEntity newEntity = new VectorEntity();
                newEntity.setFileName(fileName);
                newEntity.setContent(searchHit.getContent().getContent());
                resultMap.put(fileName, newEntity);
            }
        }

        List<VectorEntity> passages = new ArrayList<>(resultMap.values());

        int start = searchDTO.getPage() * searchDTO.getPageSize();
        int end = Math.min(start + searchDTO.getPageSize(), passages.size());

//        return passages;
        if (start <= end) {
            List<VectorEntity> content = passages.subList(start, end);
            Page<VectorEntity> userDtoPage = new PageImpl<>(
                    content,
                    PageRequest.of(searchDTO.getPage(), searchDTO.getPageSize()),
                    passages.size()
            );
            return new ServiceResult<>(
                    userDtoPage,
                    HttpStatus.OK,
                    "success")
                    ;
        } else {
            System.out.println("Invalid start and end values.");
            return new ServiceResult<>(
                    new PageImpl<>(Collections.emptyList(), PageRequest.of(searchDTO.getPage(), searchDTO.getPageSize()), 0),
                    HttpStatus.BAD_REQUEST,"Fail"
            );
        }
    }

    @Override
    public String getFileContent(String fileName) {
        // Tạo truy vấn tìm kiếm các index có cùng "fileName"
        QueryBuilder queryBuilder = QueryBuilders.matchQuery("file_name", fileName);

        // Sắp xếp theo "trunkCount" tăng dần
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .withSort(SortBuilders.fieldSort("trunk_count").order(SortOrder.ASC))
                .build();

        // Thực thi truy vấn tìm kiếm
        SearchHits<VectorEntity> searchHits =
                elasticsearchOperations
                        .search(searchQuery, VectorEntity.class,
                                IndexCoordinates.of(MESSAGE_INDEX));

        // Lấy ra nội dung "content" từ các index và cộng chuỗi lại
        StringBuilder fileContent = new StringBuilder();
        for (SearchHit<VectorEntity> searchHit : searchHits) {
            fileContent.append(searchHit.getContent().getContent());
        }

        return fileContent.toString();
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


    @Override
    public void deleteByFileName(String fileName) {
        // Tạo truy vấn tìm kiếm để lấy danh sách các bản ghi cần xóa
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("file_name.keyword", fileName))
                .build();

        // Thực hiện truy vấn tìm kiếm
        SearchHits<VectorEntity> searchHits = elasticsearchOperations.search(searchQuery, VectorEntity.class,
                IndexCoordinates.of(MESSAGE_INDEX));

        List<String> idsToDelete = searchHits.stream()
                .map(SearchHit::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (!idsToDelete.isEmpty() && !idsToDelete.contains(null)) {
            for (String id : idsToDelete) {
                elasticsearchOperations.delete(id, IndexCoordinates.of(MESSAGE_INDEX));
            }
        }
    }


}
