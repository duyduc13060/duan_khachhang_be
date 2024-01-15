package com.example.du_an_demo_be.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchDTO<T> {
    private T data;
    private Integer page;
    private Integer pageSize;
}
