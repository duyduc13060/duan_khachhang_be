package com.example.du_an_demo_be.repository;

import com.example.du_an_demo_be.model.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity,Long> {

    List<MessageEntity> getAllByCreator(String creator);


}
