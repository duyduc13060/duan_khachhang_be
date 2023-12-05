package com.example.du_an_demo_be.service;

import com.example.du_an_demo_be.model.dto.MessageDto;
import com.example.du_an_demo_be.payload.request.ChatBoxRequest;
import com.example.du_an_demo_be.payload.response.ChatBoxResponse;
import com.example.du_an_demo_be.payload.response.ServiceResult;

import java.util.List;

public interface MessageService {
    List<MessageDto> getListMessage();

    ServiceResult<ChatBoxResponse> saveMessage(ChatBoxRequest chatBoxRequest);
}
