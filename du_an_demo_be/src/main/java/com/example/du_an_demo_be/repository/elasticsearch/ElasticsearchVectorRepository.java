package com.example.du_an_demo_be.repository.elasticsearch;

import com.example.du_an_demo_be.model.entity.VectorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ElasticsearchVectorRepository implements ElasticsearchRepository<VectorEntity, String> {
    @Override
    public Page<VectorEntity> searchSimilar(VectorEntity vectorEntity, String[] strings, Pageable pageable) {
        return null;
    }

    @Override
    public Iterable<VectorEntity> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<VectorEntity> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends VectorEntity> S save(S s) {
        return null;
    }

    @Override
    public <S extends VectorEntity> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<VectorEntity> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<VectorEntity> findAll() {
        return null;
    }

    @Override
    public Iterable<VectorEntity> findAllById(Iterable<String> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(VectorEntity vectorEntity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> iterable) {

    }

    @Override
    public void deleteAll(Iterable<? extends VectorEntity> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
