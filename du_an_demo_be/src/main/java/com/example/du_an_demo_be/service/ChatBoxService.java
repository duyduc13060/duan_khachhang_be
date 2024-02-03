package com.example.du_an_demo_be.service;

import com.example.du_an_demo_be.payload.request.ChatBoxGeminiProRequest;
import com.example.du_an_demo_be.payload.request.ChatBoxRequest;
import com.example.du_an_demo_be.payload.request.amazon.ChatBoxAmazonRequest;
import com.example.du_an_demo_be.payload.response.DefaultResponse;
import com.example.du_an_demo_be.payload.response.ResultApiChatBox;

public interface ChatBoxService {
    DefaultResponse<ResultApiChatBox> chatBox(ChatBoxRequest chatBoxRequest, String api, String token);

    DefaultResponse<ResultApiChatBox> chatBoxCallRestTemplate(ChatBoxRequest chatBoxRequest, String UrlApi, String token);

    DefaultResponse<ResultApiChatBox> chatBoxAmazon(ChatBoxAmazonRequest chatBoxRequest, String UrlApi);

    DefaultResponse<ResultApiChatBox> chatBoxTest(ChatBoxAmazonRequest chatBoxRequest, String UrlApi);

    DefaultResponse<ResultApiChatBox> chatBoxGeminiPro(ChatBoxGeminiProRequest chatBoxRequest, String UrlApi);
}
