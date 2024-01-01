package com.example.du_an_demo_be.service;

import com.example.du_an_demo_be.model.entity.VectorEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VectorService {
//    void processAndSaveVectors(List<String> docs);
//
//    List<String> querySimilarVectors(String query);
//
//    void processAndSaveVectorsNew(String doc);

    String uploadFile(MultipartFile file);

//    List<VectorEntity> processSearch(String query);
}
