package com.example.du_an_demo_be.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponseDTO<T> {
    private Long total;
    private List<T> data;
    private Integer totalPage;
    private Integer page;
    private Integer pageSize;
}
