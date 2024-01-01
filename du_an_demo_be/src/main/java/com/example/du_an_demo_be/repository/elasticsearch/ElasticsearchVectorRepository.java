package com.example.du_an_demo_be.repository.elasticsearch;

import com.example.du_an_demo_be.model.entity.VectorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ElasticsearchVectorRepository extends ElasticsearchRepository<VectorEntity, String>  {

}
