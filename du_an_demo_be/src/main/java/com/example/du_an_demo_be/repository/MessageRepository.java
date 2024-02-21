package com.example.du_an_demo_be.repository;

import com.example.du_an_demo_be.model.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity,Long> {

    List<MessageEntity> getAllByCreatorAndType(String creator, Integer type);

}
