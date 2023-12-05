package com.example.du_an_demo_be.payload.response;

import lombok.*;

import java.util.List;

@Data
public class ChatBoxResponse {
    private String id;
    private String model;
    private Integer created;
    private Usage usage;
    private String object;
    private List<Choices> choices;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Usage{
        private Integer prompt_tokens;
        private Integer completion_tokens;
        private Integer total_tokens;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Choices{
        private Integer index;
        private String finish_reason;
        private Message message;
        private Delta delta;
    }


    @Data
    public static class Message{
        private String role;
        private String content;
    }

    @Data
    public static class Delta{
        private String role;
        private String content;
    }


}
