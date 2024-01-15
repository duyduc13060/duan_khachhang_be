package com.example.du_an_demo_be.repository;

import com.example.du_an_demo_be.model.dto.ClickLikeDto;
import com.example.du_an_demo_be.model.entity.ClickLikeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClickLikeRepository extends JpaRepository<ClickLikeEntity,Long> {


    @Query(value = "select * from click_likes cl where cl.username_like = :usernameLike",nativeQuery = true)
    Optional<ClickLikeEntity> findByUsernameLike(@Param("usernameLike") String usernameLike);

    @Query( "SELECT new com.example.du_an_demo_be.model.dto.ClickLikeDto(cl.id,cl.createDate,cl.usernameLike) " +
            "FROM ClickLikeEntity cl " +
            "WHERE cl.promptId = :promptId")
    Page<ClickLikeDto> listUsernameClickLike(
            @Param("promptId") Long promptId,
            Pageable pageable

    );


    @Query(value = "select * from click_likes cl where cl.username_like = :usernameLike and cl.prompt_id = :promptId",nativeQuery = true)
    Optional<ClickLikeEntity> findByUsernameLikeAndPromptId(
            @Param("usernameLike") String usernameLike,
            @Param("promptId") Long promptId
    );

    @Query( "SELECT new com.example.du_an_demo_be.model.dto.ClickLikeDto(cl.promptId,count(cl.promptId) as numberLike) " +
            "FROM ClickLikeEntity cl " +
            "WHERE cl.promptId = :promptId")
    ClickLikeDto countNumberLike( @Param("promptId") Long promptId);

}
