package com.example.du_an_demo_be.repository;

import com.example.du_an_demo_be.model.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends CrudRepository<MessageEntity, Long> {
    @Query(value = "SELECT * FROM messages WHERE creator = ?1 AND type = ?2 ORDER BY create_time DESC LIMIT 50", nativeQuery = true)
    List<MessageEntity> findTop50ByCreatorAndTypeOrderByCreateTimeDesc(String creator, Integer type);
}