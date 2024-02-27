package com.example.du_an_demo_be.controller;


import com.example.du_an_demo_be.model.dto.ElasticSearchDto;
import com.example.du_an_demo_be.model.dto.UserDto;
import com.example.du_an_demo_be.payload.request.SearchDTO;
import com.example.du_an_demo_be.service.VectorService;
import com.example.du_an_demo_be.service.elasticsearch.VectorSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/QLQS")
@RequiredArgsConstructor
public class VectorController {

    private final VectorService vectorService;
    private final VectorSearchService vectorSearchService;

    @PostMapping("/import/upload")
    public ResponseEntity<String> handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("documentGroup") String documentGroup
    ) {
        try {
            System.out.println("Document Group: " + documentGroup);
            return ResponseEntity.ok().body(this.vectorService.uploadFile(file, documentGroup));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Lỗi khi xử lý file");
        }
    }

    @PostMapping("/search/vector")
    public ResponseEntity<?> fetchSuggestions(
            @RequestBody ElasticSearchDto elasticSearchDto
            ){
        return ResponseEntity.ok().body(this.vectorSearchService.processSearch(elasticSearchDto.getContent()));
    }

    @DeleteMapping("/delete/message_index")
    public ResponseEntity<?> deleteDocument() {
        vectorSearchService.deleteAllIndex();
        return ResponseEntity.ok().body("Xóa thành công");
    }

    @PostMapping("/get/original/file")
    public ResponseEntity<?> getOriginalFile(
            @RequestBody ElasticSearchDto elasticSearchDto
    ){
        String fileContent = this.vectorSearchService.getFileContent(elasticSearchDto.getFilename());
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("fileContent", fileContent);
        responseBody.put("status", "success");
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    @PostMapping("/search/vector/creator")
    public ResponseEntity<?> searchCreator(
            @RequestBody SearchDTO<ElasticSearchDto> searchDTO
    ){
        return ResponseEntity.ok().body(this.vectorSearchService.getAllDocumentInfor(searchDTO));
    }

    @DeleteMapping("/delete/vector/filename")
    public ResponseEntity<?> deleteFilename(
            @RequestParam("filename") String filename
    ){
        this.vectorSearchService.deleteByFileName(filename);
        return ResponseEntity.ok().body("success");
    }
}
