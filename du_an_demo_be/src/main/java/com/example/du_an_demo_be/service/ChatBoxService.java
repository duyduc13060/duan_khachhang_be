package com.example.du_an_demo_be.service;

import com.example.du_an_demo_be.payload.request.ChatBoxRequest;
import com.example.du_an_demo_be.payload.response.DefaultResponse;
import com.example.du_an_demo_be.payload.response.ResultApiChatBox;

public interface ChatBoxService {
    DefaultResponse<ResultApiChatBox> chatBox(ChatBoxRequest chatBoxRequest, String api, String token);
}
