package com.example.du_an_demo_be.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClickLikeDto {
    private Long id;
    private LocalDateTime createDate;
    private String usernameLike;
    private Long promptId;
    private Integer page;
    private Integer pageSize;
    private Long numberLike;

    public ClickLikeDto(Long promptId, Long numberLike) {
        this.promptId = promptId;
        this.numberLike = numberLike;
    }

    public ClickLikeDto(Long id, LocalDateTime createDate, String usernameLike) {
        this.id = id;
        this.createDate = createDate;
        this.usernameLike = usernameLike;
    }

    public ClickLikeDto(Long id, LocalDateTime createDate, String usernameLike, Long promptId) {
        this.id = id;
        this.createDate = createDate;
        this.usernameLike = usernameLike;
        this.promptId = promptId;
    }
}
