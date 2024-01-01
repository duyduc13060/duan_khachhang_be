package com.example.du_an_demo_be.service.impl;

import com.example.du_an_demo_be.exception.BadRequestException;
import com.example.du_an_demo_be.model.entity.VectorEntity;
import com.example.du_an_demo_be.repository.elasticsearch.ElasticsearchVectorRepository;
import com.example.du_an_demo_be.service.VectorService;
import com.example.du_an_demo_be.service.elasticsearch.VectorSearchService;
import lombok.RequiredArgsConstructor;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class VectorServiceImpl implements VectorService {

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
    private final ElasticsearchVectorRepository elasticsearchJpaVectorRepository;
    private final VectorSearchService vectorSearchService;
    private static final String VECTOR_INDEX = "vector_index";


//    @Override
//    public void processAndSaveVectors(List<String> docs) {
//        for (String doc : docs) {
//            List<String> trunks = splitIntoTrunks(doc, 1000);
//
//            // Lưu các đoạn vào cơ sở dữ liệu
//            for (String trunk : trunks) {
//                TrunkEntity trunkEntity = new TrunkEntity();
//                trunkEntity.setTrunkContent(trunk);
//                trunkRepository.save(trunkEntity);
//
//                VectorEntity vectorEntity = new VectorEntity();
//                vectorEntity.setDocument(trunks.toString());
//                vectorEntity.setVector(Arrays.asList(trunkEntity.getId().toString()));
//                VectorEntity savedVectorEntity = jpaVectorRepository.save(vectorEntity);
//
//                // Lưu cập nhật dữ liệu vào Elasticsearch
//                elasticsearchJpaVectorRepository.save(savedVectorEntity);
//            }
//        }
//    }
//
//
//    @Override
//    public List<String> querySimilarVectors(String query) {
//
//        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.matchQuery("document", query))
//                .build();
//
//        System.out.println("Elasticsearch Query: " + nativeSearchQuery.getQuery().toString());
//
//        // Tạo một truy vấn Elasticsearch
//        SearchHits<VectorEntity> searchHits = elasticsearchRestTemplate.search(
//                new org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder()
//                        .withQuery(QueryBuilders.matchQuery("document", query))
//                        .build(), VectorEntity.class);
//
//        List<String> results = new ArrayList<>();
//        for (SearchHit<VectorEntity> searchHit : searchHits) {
//            VectorEntity vectorEntity = searchHit.getContent();
//            // Lấy danh sách các đoạn gần đúng nhất từ vector DB
//            List<String> trunkIds = vectorEntity.getVector();
//            for (String trunkId : trunkIds) {
//                TrunkEntity trunkEntity = trunkRepository.findById(Long.parseLong(trunkId)).orElse(null);
//                if (trunkEntity != null) {
//                    results.add(trunkEntity.getTrunkContent());
//                }
//            }
//        }
//        return results;
//    }
//
//
//    private List<String> splitIntoTrunks(String text, int trunkLength) {
//        List<String> trunks = new ArrayList<>();
//        for (int i = 0; i < text.length(); i += trunkLength) {
//            int endIndex = Math.min(i + trunkLength, text.length());
//            trunks.add(text.substring(i, endIndex));
//        }
//        return trunks;
//    }
//
//
//    @Override
//    public void processAndSaveVectorsNew(String doc) {
//        List<String> trunks = this.splitIntoTrunks1(doc, 1000);
//
//        // Lưu các đoạn vào cơ sở dữ liệu
//        for (String trunk : trunks) {
//            TrunkEntity trunkEntity = new TrunkEntity();
//            trunkEntity.setTrunkContent(trunk);
//            trunkRepository.save(trunkEntity);
//
//            VectorEntity vectorEntity = new VectorEntity();
//            vectorEntity.setDocument(doc);
//            vectorEntity.setVector(Arrays.asList(trunkEntity.getId().toString()));
//            VectorEntity savedVectorEntity = jpaVectorRepository.save(vectorEntity);
//
//            // TODO: Lưu cập nhật dữ liệu vào Elasticsearch
//            elasticsearchJpaVectorRepository.save(savedVectorEntity);
//        }
//    }
//
//    private List<String> splitIntoTrunks1(String doc, int trunkSize) {
//        // Cắt đoạn văn bản thành các đoạn nhỏ (trunks) với độ dài trunkSize
//        List<String> trunks = new ArrayList<>();
//        for (int i = 0; i < doc.length(); i += trunkSize) {
//            int end = Math.min(i + trunkSize, doc.length());
//            trunks.add(doc.substring(i, end));
//        }
//        return trunks;
//    }

    @Override
    public String uploadFile(MultipartFile file){
        try {
            String content = "";
            InputStream inputStream = file.getInputStream();

            // Create a metadata object to hold document metadata
            Metadata metadata = new Metadata();

            // Create a content handler to extract the text
            BodyContentHandler handler = new BodyContentHandler(Integer.MAX_VALUE); // Set a high character limit

            // Create a ParseContext
            ParseContext parseContext = new ParseContext();

            // Create a parser (AutoDetectParser can automatically detect the document type)
            Parser parser = new AutoDetectParser();

            // Parse the document
            parser.parse(inputStream, handler, metadata, parseContext);

            // Get the extracted text
            content = handler.toString();

            if(!content.isEmpty() || content != null){
                //todo: lưu data trong file vào vector
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
                this.vectorSearchService.createProductIndex(vector);
//                this.processAndSaveVectorsNew(content);
            }else{
                throw new BadRequestException("Content is null");
            }

            // Close the input stream
            inputStream.close();

            return content;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Lỗi khi xử lý file");
        }
    }


//    @Override
//    public List<VectorEntity> processSearch(final String query) {
//
//        // 1. Create query on multiple fields enabling fuzzy search
//        QueryBuilder queryBuilder =
//                QueryBuilders
//                        .multiMatchQuery(query, "document")
//                        .fuzziness(Fuzziness.AUTO);
//
//        Query searchQuery = new NativeSearchQueryBuilder()
//                .withFilter(queryBuilder)
//                .build();
//
//        // 2. Execute search
//        SearchHits<VectorEntity> productHits =
//                elasticsearchRestTemplate
//                        .search(searchQuery, VectorEntity.class,
//                                IndexCoordinates.of(VECTOR_INDEX));
//
//        // 3. Map searchHits to product list
//        List<VectorEntity> productMatches = new ArrayList<VectorEntity>();
//        productHits.forEach(searchHit->{
//            productMatches.add(searchHit.getContent());
//        });
//        return productMatches;
//    }


}
