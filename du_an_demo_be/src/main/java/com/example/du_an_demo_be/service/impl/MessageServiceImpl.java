package com.example.du_an_demo_be.service.impl;

import com.example.du_an_demo_be.config.Constants;
import com.example.du_an_demo_be.model.dto.MessageDto;
import com.example.du_an_demo_be.model.entity.MessageEntity;
import com.example.du_an_demo_be.payload.request.ChatBoxGeminiProRequest;
import com.example.du_an_demo_be.payload.request.ChatBoxRequest;
import com.example.du_an_demo_be.payload.request.amazon.ChatBoxAmazonRequest;
import com.example.du_an_demo_be.payload.response.*;
import com.example.du_an_demo_be.payload.response.amazon.ChatBoxAmazonResponse;
import com.example.du_an_demo_be.repository.MessageRepository;
import com.example.du_an_demo_be.security.CustomerDetailService;
import com.example.du_an_demo_be.service.ChatBoxService;
import com.example.du_an_demo_be.service.MessageService;
import com.example.du_an_demo_be.until.CurrentUserUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ChatBoxService chatBoxService;
    private final ModelMapper modelMapper;
    private final String GEMINI_PRO = "GEMINI_PRO";

    @Value("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")
    public String tokenCompletions;

    @Value("https://api.perplexity.ai/chat/completions")
    public String apiChatBox;

    @Value("https://oqt1iem2ub.execute-api.us-east-1.amazonaws.com/Product/bedrock")
    public String apiChatBoxAmazon;

    @Value("https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=AIzaSyB8zxF5HGOxdhm4QIcJAp_j_8C3CACOXO0")
    public String apiChatBoxGeminiPro;

    @Override
    public List<MessageDto> getListMessage(Integer type) {
        CustomerDetailService customerDetailService = CurrentUserUtils.getCurrentUserUtils();

        List<MessageDto> messageDtoList = messageRepository.findTop50ByCreatorAndTypeOrderByCreateTimeDesc(customerDetailService.getUsername(), type)
                .stream()
                .map(e -> modelMapper.map(e, MessageDto.class))
                .collect(Collectors.toList());

        // Đảo ngược thứ tự của list để có thứ tự create_time tăng dần
        Collections.reverse(messageDtoList);

        return messageDtoList;
    }


    @Override
    public ServiceResult<ChatBoxResponse> saveMessage(ChatBoxRequest chatBoxRequest){
        ServiceResult serviceResult = new ServiceResult<>();
        DefaultResponse<ResultApiChatBox> response = chatBoxService.chatBox(chatBoxRequest, apiChatBox,tokenCompletions);

        CustomerDetailService customerDetailService = CurrentUserUtils.getCurrentUserUtils();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        if(response.getSuccess() != 200){
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
            serviceResult.setCode(Constants.CODE_FAIL);
            serviceResult.setMessage("Call api chat bõ thất bại");
            return serviceResult;
        }

        String jsonContent = response.getData().getJsonObjectString();
        try{
             ChatBoxResponse chatBoxResponse =  objectMapper.readValue(jsonContent, ChatBoxResponse.class);

            for (ChatBoxResponse.Choices message : chatBoxResponse.getChoices()) {
                MessageEntity messageEntity = MessageEntity.builder()
                        .contentResponse(message.getMessage().getContent())
                        .contentRequest(chatBoxRequest.getMessages().get(1).getContent())
                        .model(chatBoxResponse.getModel())
                        .completionsId(chatBoxResponse.getId())
                        .roleChoices(message.getMessage().getRole())
                        .creator(customerDetailService.getUsername())
                        .build();
                this.messageRepository.save(messageEntity);
            }

            return new ServiceResult<>( chatBoxResponse,HttpStatus.OK,"Thành công");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new ServiceResult<>( null ,HttpStatus.OK,"Thất bại");
    }


    @Override
    public ServiceResult<ChatBoxResponse> saveMessageRestTemplate(ChatBoxRequest chatBoxRequest){
        ServiceResult serviceResult = new ServiceResult<>();
        DefaultResponse<ResultApiChatBox> response = chatBoxService.chatBoxCallRestTemplate(chatBoxRequest, apiChatBox,tokenCompletions);

        CustomerDetailService customerDetailService = CurrentUserUtils.getCurrentUserUtils();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        if(response.getSuccess() != 200){
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
            serviceResult.setCode(Constants.CODE_FAIL);
            serviceResult.setMessage("Call api chat box thất bại");
            return serviceResult;
        }

        try {
            JSONObject jsonContent1 = response.getData().getJsonObject();

            Gson gson = new Gson();
            ChatBoxResponse chatBoxResponse = gson.fromJson(jsonContent1.toString(), ChatBoxResponse.class);
            for (ChatBoxResponse.Choices message : chatBoxResponse.getChoices()) {
                MessageEntity messageEntity = MessageEntity.builder()
                        .contentResponse(message.getMessage().getContent())
                        .contentRequest(chatBoxRequest.getMessages().get(1).getContent())
                        .model(chatBoxResponse.getModel())
                        .completionsId(chatBoxResponse.getId())
                        .roleChoices(message.getMessage().getRole())
                        .creator(customerDetailService.getUsername())
                        .type(chatBoxRequest.getType())
                        .build();
                this.messageRepository.save(messageEntity);
            }
            return new ServiceResult<>( chatBoxResponse,HttpStatus.OK,"Thành công");

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return new ServiceResult<>( null ,HttpStatus.OK,"Thất bại");
    }

    @Override
    public ServiceResult<ChatBoxResponse> saveMessageRestTemplate1(ChatBoxRequest chatBoxRequest){
        ServiceResult serviceResult = new ServiceResult<>();
        DefaultResponse<ResultApiChatBox> response = chatBoxService.chatBoxCallRestTemplate(chatBoxRequest, apiChatBox,tokenCompletions);

        CustomerDetailService customerDetailService = CurrentUserUtils.getCurrentUserUtils();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        if(response.getSuccess() != 200){
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
            serviceResult.setCode(Constants.CODE_FAIL);
            serviceResult.setMessage("Call api chat box thất bại");
            return serviceResult;
        }

        try {
            JSONObject jsonContent1 = response.getData().getJsonObject();

            Gson gson = new Gson();
            ChatBoxResponse chatBoxResponse = gson.fromJson(jsonContent1.toString(), ChatBoxResponse.class);

            return new ServiceResult<>( chatBoxResponse,HttpStatus.OK,"Thành công");

        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ServiceResult<>( null ,HttpStatus.OK,"Thất bại");
    }

    @Override
    public ServiceResult<ChatBoxAmazonResponse> saveMessageChatBoxAmazon(ChatBoxAmazonRequest chatBoxRequest){
        ServiceResult serviceResult = new ServiceResult<>();
//        DefaultResponse<ResultApiChatBox> response = chatBoxService.chatBoxAmazon(chatBoxRequest, apiChatBoxAmazon);
        DefaultResponse<ResultApiChatBox> response = chatBoxService.chatBoxTest(chatBoxRequest, apiChatBoxAmazon);

        CustomerDetailService customerDetailService = CurrentUserUtils.getCurrentUserUtils();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        if(response.getSuccess() != 200){
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
            serviceResult.setCode(Constants.CODE_FAIL);
            serviceResult.setMessage("Call api chat box thất bại");
            return serviceResult;
        }

        try {

            JSONObject jsonContent1 = response.getData().getJsonObject();

            JSONObject jsonBody = jsonContent1.getJSONObject("body");

            Gson gson = new Gson();
            ChatBoxAmazonResponse chatBoxResponse = gson.fromJson(jsonBody.toString(), ChatBoxAmazonResponse.class);

            MessageEntity messageEntity = MessageEntity.builder()
                    .contentResponse(chatBoxResponse.getCompletion())
                    .contentRequest(chatBoxRequest.getPrompt())
                    .model(chatBoxRequest.getKey())
                    .creator(customerDetailService.getUsername())
                    .type(1)
                    .build();
            this.messageRepository.save(messageEntity);

            return new ServiceResult<>( chatBoxResponse,HttpStatus.OK,"Thành công");
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ServiceResult<>( null ,HttpStatus.OK,"Thất bại");
    }

    @Override
    public ServiceResult<ChatBoxGeminiProResponse> saveMessageChatBoxGeminiPro(ChatBoxGeminiProRequest chatBoxRequest){
        ServiceResult serviceResult = new ServiceResult<>();
        DefaultResponse<ResultApiChatBox> response = chatBoxService.chatBoxGeminiPro(chatBoxRequest, apiChatBoxGeminiPro);

        CustomerDetailService customerDetailService = CurrentUserUtils.getCurrentUserUtils();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        if(response.getSuccess() != 200){
            serviceResult.setStatus(HttpStatus.BAD_REQUEST);
            serviceResult.setCode(Constants.CODE_FAIL);
            serviceResult.setMessage("Call api generate message thất bại");
            return serviceResult;
        }

        try {

            JSONObject jsonContent1 = response.getData().getJsonObject();
            JSONArray candidates =  jsonContent1.getJSONArray("candidates");
            JSONObject candidate = candidates.getJSONObject(0);
            JSONObject content = candidate.getJSONObject("content");
            JSONArray parts = content.getJSONArray("parts");
            JSONObject part = parts.getJSONObject(0);
            String text = part.getString("text");

            // Khởi tạo đối tượng ChatBoxGeminiProResponse và gán giá trị text
            ChatBoxGeminiProResponse chatBoxGeminiProResponse = new ChatBoxGeminiProResponse();
            chatBoxGeminiProResponse.setText(text);

            MessageEntity messageEntity = MessageEntity.builder()
                    .contentResponse(text)
                    .contentRequest(chatBoxRequest.getContents().get(0).getParts().get(0).getText())
                    .model(GEMINI_PRO)
                    .creator(customerDetailService.getUsername())
                    .type(2)
                    .build();
            this.messageRepository.save(messageEntity);

            return new ServiceResult<>( chatBoxGeminiProResponse,HttpStatus.OK,"Thành công");
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ServiceResult<>( null ,HttpStatus.OK,"Thất bại");
    }

}
