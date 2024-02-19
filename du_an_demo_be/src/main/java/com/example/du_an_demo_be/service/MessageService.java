package com.example.du_an_demo_be.service;

import com.example.du_an_demo_be.model.dto.MessageDto;
import com.example.du_an_demo_be.payload.request.ChatBoxGeminiProRequest;
import com.example.du_an_demo_be.payload.request.ChatBoxRequest;
import com.example.du_an_demo_be.payload.request.amazon.ChatBoxAmazonRequest;
import com.example.du_an_demo_be.payload.response.ChatBoxGeminiProResponse;
import com.example.du_an_demo_be.payload.response.ChatBoxResponse;
import com.example.du_an_demo_be.payload.response.ServiceResult;
import com.example.du_an_demo_be.payload.response.amazon.ChatBoxAmazonResponse;

import java.util.List;

public interface MessageService {
    List<MessageDto> getListMessage(Integer type);

    ServiceResult<ChatBoxResponse> saveMessage(ChatBoxRequest chatBoxRequest);

    ServiceResult<ChatBoxResponse> saveMessageRestTemplate(ChatBoxRequest chatBoxRequest);

    ServiceResult<ChatBoxAmazonResponse> saveMessageChatBoxAmazon(ChatBoxAmazonRequest chatBoxRequest);

    ServiceResult<ChatBoxGeminiProResponse> saveMessageChatBoxGeminiPro(ChatBoxGeminiProRequest chatBoxRequest);
}
