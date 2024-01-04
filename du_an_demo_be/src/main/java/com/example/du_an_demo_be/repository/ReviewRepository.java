package com.example.du_an_demo_be.repository;

import com.example.du_an_demo_be.model.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity,Long> {


    List<ReviewEntity> findAllByCreateNameAndMessageId(
            String createName,
            Long messageId
    );


}
