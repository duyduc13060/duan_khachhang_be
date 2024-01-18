package com.example.du_an_demo_be.repository;

import com.example.du_an_demo_be.model.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity,Long> {


    @Query("SELECT r FROM ReviewEntity r WHERE r.createName = :userName")
    List<ReviewEntity> findAllByUser(
            String userName
    );

    @Query("SELECT r FROM ReviewEntity r")
    List<ReviewEntity> findAllByRoleAdmin();

}
