package com.example.du_an_demo_be.payload.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public  class ChatBoxRequest {

    private String model;
    private List<Messages> messages;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Messages{
        private String role;
        private String content;
    }

}
