package com.example.du_an_demo_be.repository;

import com.example.du_an_demo_be.model.entity.NoteEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity, Long> {

//    @Query(value = "select \n" +
//            "\tn.id,\n" +
//            "    n.content,\n" +
//            "    n.create_date,\n" +
//            "    n.`description`,\n" +
//            "    n.title,\n" +
//            "    n.update_date,\n" +
//            "    n.user_id\n" +
//            "from notes n\n" +
//            "where user_id = :user_id", nativeQuery = true)
//    Page<NoteEntity> getNoteByUser(Long userId, Pageable pageable);

        @Query(value = "select \n" +
                "\tn.id,\n" +
                "    n.content,\n" +
                "    n.create_date,\n" +
                "    n.`description`,\n" +
                "    n.title,\n" +
                "    n.update_date,\n" +
                "    n.username \n" +
                "from notes n \n" +
                "where n.username = :username", nativeQuery = true)
    List<NoteEntity> getNoteByUsername(String username);

}
