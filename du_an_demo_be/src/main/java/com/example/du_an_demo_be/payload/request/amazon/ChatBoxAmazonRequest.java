package com.example.du_an_demo_be.payload.request.amazon;

import lombok.Data;

@Data
public class ChatBoxAmazonRequest {
    private String prompt;
    private String key;
    private Integer max;
}
