package com.example.du_an_demo_be.service;

import com.example.du_an_demo_be.model.dto.MessageDto;
import com.example.du_an_demo_be.payload.request.ChatBoxRequest;
import com.example.du_an_demo_be.payload.response.ChatBoxResponse;
import com.example.du_an_demo_be.payload.response.ServiceResult;

public interface MessageService {
    ServiceResult<ChatBoxResponse> saveMessage(ChatBoxRequest chatBoxRequest);
}
