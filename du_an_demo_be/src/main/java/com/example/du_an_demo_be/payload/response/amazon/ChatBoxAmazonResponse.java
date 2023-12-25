package com.example.du_an_demo_be.payload.response.amazon;

import lombok.Data;

@Data
public class ChatBoxAmazonResponse {
    private String completion;
    private String stop_reason;
    private String stop;
}
