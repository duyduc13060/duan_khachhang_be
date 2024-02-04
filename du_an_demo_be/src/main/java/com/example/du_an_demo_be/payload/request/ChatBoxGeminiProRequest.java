package com.example.du_an_demo_be.payload.request;
import lombok.Data;

import java.util.List;

@Data
public class ChatBoxGeminiProRequest {
    private List<Content> contents;

    @Data
    public static class Content {
        private List<Part> parts;
    }

    @Data
    public static class Part {
        private String text;
    }
}
