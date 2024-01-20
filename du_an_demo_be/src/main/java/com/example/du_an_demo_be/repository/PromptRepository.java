package com.example.du_an_demo_be.repository;


import com.example.du_an_demo_be.model.dto.PromptDto;
import com.example.du_an_demo_be.model.entity.PromptEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromptRepository extends JpaRepository<PromptEntity,Long> {


//    @Query( "SELECT new com.example.du_an_demo_be.model.dto.PromptDto( " +
//            "pt.id,pt.promptTypeId,pt.descriptionUse,pt.promptName, " +
//            "pt.createUsername,pt.createDate,pt.updateDate,pte.name,cl.numberLike " +
//            ") " +
//            "FROM PromptEntity pt " +
//            "JOIN PromptTypeEntity pte on pt.promptTypeId = pte.id " +
//            "JOIN ( SELECT c.promptId, COUNT(c.promptId) as numberLike " +
//            " FROM com.example.du_an_demo_be.model.entity.ClickLikeEntity c " +
//            " GROUP BY c.promptId " +
//            ") as cl on cl.promptId = pt.id " +
//            "WHERE (1 = 1 " +
//            " AND(:promptTypeId IS NULL OR pt.promptTypeId = :promptTypeId) " +
//            " AND(:promptName IS NULL OR pt.promptName LIKE CONCAT('%', :promptName, '%') OR pt.descriptionUse LIKE CONCAT('%', :promptName, '%')) " +
//            " and (:createDate is null or STR_TO_DATE(DATE_FORMAT(pt.createDate, '%Y/%m/%d'), '%Y/%m/%d') = STR_TO_DATE(:createDate , '%Y/%m/%d')) " +
//            ")"
//    )
    @Query( "SELECT new com.example.du_an_demo_be.model.dto.PromptDto( " +
            "pt.id,pt.promptTypeId,pt.descriptionUse,pt.promptName, " +
            "pt.createUsername,pt.createDate,pt.updateDate,pte.name" +
            ") " +
            "FROM PromptEntity pt " +
            "JOIN PromptTypeEntity pte on pt.promptTypeId = pte.id " +
            "WHERE (1 = 1 " +
            " AND(:promptTypeId IS NULL OR pt.promptTypeId = :promptTypeId) " +
            " AND(:promptName IS NULL OR pt.promptName LIKE CONCAT('%', :promptName, '%') OR pt.descriptionUse LIKE CONCAT('%', :promptName, '%')) " +
            " and (:createDate is null or STR_TO_DATE(DATE_FORMAT(pt.createDate, '%Y/%m/%d'), '%Y/%m/%d') = STR_TO_DATE(:createDate , '%Y/%m/%d')) " +
            ") " +
            "ORDER BY pt.id DESC"
    )
    Page<PromptDto> searchPrompt(
            @Param("promptTypeId") Long promptTypeId,
            @Param("promptName") String productName,
            @Param("createDate") String createDate,
            Pageable pageable
    );

}
