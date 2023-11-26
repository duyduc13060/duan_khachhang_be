package com.example.du_an_demo_be.payload.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SampleResponse {
    private Boolean success;
    private String message;
    private Object data;

}
