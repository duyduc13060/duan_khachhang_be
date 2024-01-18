package com.example.du_an_demo_be.controller;


import com.example.du_an_demo_be.model.dto.ElasticSearchDto;
import com.example.du_an_demo_be.service.VectorService;
import com.example.du_an_demo_be.service.elasticsearch.VectorSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/QLQS")
@RequiredArgsConstructor
public class VectorController {

    private final VectorService vectorService;
    private final VectorSearchService vectorSearchService;

//    @PostMapping("/search")
//    public List<VectorEntity> search(@RequestBody String query) {
//        List<VectorEntity> results = vectorService.processSearch(query);
//        return results;
//    }
//
//    @PostMapping("/search-new")
//    public List<String> searchNew(@RequestBody String query) {
//        List<String> results = vectorService.querySimilarVectors(query);
//        return results;
//    }
//
//
//    @PostMapping("/save-vectors")
//    public void saveVectors(@RequestBody List<String> docs) {
//        vectorService.processAndSaveVectors(docs);
//    }
//
//
//    @PostMapping("/save-vectors-new")
//    public void saveVectors(@RequestBody String docs) {
//        vectorService.processAndSaveVectorsNew(docs);
//    }



    // TODO: upload file
//    @PostMapping("/files/upload")
//    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
//        try {
//            String content = "";
//            String fileName = file.getOriginalFilename();
//            InputStream inputStream = file.getInputStream();
//
//            // Đọc nội dung của file dựa trên loại file
//            if (fileName.endsWith(".txt") || fileName.endsWith(".pdf")) {
//                content = IOUtils.toString(inputStream, "UTF-8");
//            } else if (fileName.endsWith(".docx")) {
//                XWPFDocument doc = new XWPFDocument(inputStream);
//                XWPFWordExtractor extractor = new XWPFWordExtractor(doc);
//                content = extractor.getText();
//                extractor.close();
//            }
//
//            // Xử lý nội dung (ví dụ: in ra console)
//            System.out.println("Nội dung file:\n" + content);
//
//            return ResponseEntity.ok("Nội dung file:\n" + content);
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body("Lỗi khi xử lý file");
//        }
//    }

    @PostMapping("/import/upload")
    public ResponseEntity<String> handleFileUpload(
            @RequestParam("file") MultipartFile file
    ) {
        try {
            return ResponseEntity.ok().body(this.vectorService.uploadFile(file));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Lỗi khi xử lý file");
        }
    }

    @PostMapping("/search/vector")
    public ResponseEntity<?> fetchSuggestions(
            @RequestBody ElasticSearchDto elasticSearchDto
            ){
        return ResponseEntity.ok().body(this.vectorSearchService.processSearch(elasticSearchDto.getDocument()));
    }

    @DeleteMapping("/delete/message_index")
    public ResponseEntity<?> deleteDocument() {
        vectorSearchService.deleteDocumentIndex();
        return ResponseEntity.ok().body("Xóa thành công");
    }

}
