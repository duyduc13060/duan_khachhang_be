package com.example.du_an_demo_be.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromptDto {

    private Long id;
    private Long promptTypeId;
    private String descriptionUse;
    private String promptName;
    private String createUsername;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private Integer page;
    private Integer pageSize;
    private String promptTypeName;
    private String searchDate;

    public PromptDto(Long id, Long promptTypeId, String descriptionUse, String promptName, String createUsername, LocalDateTime createDate, LocalDateTime updateDate) {
        this.id = id;
        this.promptTypeId = promptTypeId;
        this.descriptionUse = descriptionUse;
        this.promptName = promptName;
        this.createUsername = createUsername;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public PromptDto(Long id, Long promptTypeId, String descriptionUse, String promptName, String createUsername, LocalDateTime createDate, LocalDateTime updateDate, String promptTypeName) {
        this.id = id;
        this.promptTypeId = promptTypeId;
        this.descriptionUse = descriptionUse;
        this.promptName = promptName;
        this.createUsername = createUsername;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.promptTypeName = promptTypeName;
    }
}
