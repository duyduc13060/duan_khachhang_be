package com.example.du_an_demo_be.repository;

import com.example.du_an_demo_be.model.entity.VectorEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface JpaVectorRepository extends JpaRepository<VectorEntity, Long> {
}
