package com.example.du_an_demo_be.repository;

import com.example.du_an_demo_be.model.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    @Query(value = "SELECT t.id,\n" +
            "\t   t.create_date,\n" +
            "       t.`description`,\n" +
            "       t.due_date,\n" +
            "       t.note_id,\n" +
            "       t.priority,\n" +
            "       t.`status`,\n" +
            "       t.title,\n" +
            "       t.update_date,\n" +
            "       t.username\n" +
            "FROM tasks t\n" +
            "where t.username = :username", nativeQuery = true)
    List<TaskEntity> getTaskByUsername(String username);

}
