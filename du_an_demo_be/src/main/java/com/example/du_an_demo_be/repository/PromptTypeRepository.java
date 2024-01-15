package com.example.du_an_demo_be.repository;

import com.example.du_an_demo_be.model.entity.PromptTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromptTypeRepository extends JpaRepository<PromptTypeEntity,Long> {

}
