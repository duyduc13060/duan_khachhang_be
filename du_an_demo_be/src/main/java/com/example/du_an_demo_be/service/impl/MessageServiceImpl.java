package com.example.du_an_demo_be.service.impl;

import com.example.du_an_demo_be.config.Constants;
import com.example.du_an_demo_be.model.dto.MessageDto;
import com.example.du_an_demo_be.model.entity.MessageEntity;
import com.example.du_an_demo_be.payload.request.ChatBoxRequest;
import com.example.du_an_demo_be.payload.response.ChatBoxResponse;
import com.example.du_an_demo_be.payload.response.DefaultResponse;
import com.example.du_an_demo_be.payload.response.ResultApiChatBox;
import com.example.du_an_demo_be.payload.response.ServiceResult;
import com.example.du_an_demo_be.repository.MessageRepository;
import com.example.du_an_demo_be.service.ChatBoxService;
import com.example.du_an_demo_be.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ChatBoxService chatBoxService;
    private final ModelMapper modelMapper;

    @Value("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")
    public String tokenCompletions;

    @Value("https://api.perplexity.ai/chat/completions")
    public String apiChatBox;



    @Override
    public List<MessageDto> getListMessage(){
        List<MessageDto> messageDtoList = messageRepository.findAll()
                .stream()
                .map(e -> modelMapper.map(e, MessageDto.class))
                .collect(Collectors.toList());

        return messageDtoList;
    }


    @Override
    public ServiceResult<ChatBoxResponse> saveMessage(ChatBoxRequest chatBoxRequest){
        ServiceResult serviceResult = new ServiceResult<>();
        DefaultResponse<ResultApiChatBox> response = chatBoxService.chatBox(chatBoxRequest, apiChatBox,tokenCompletions);

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
                        .build();
                this.messageRepository.save(messageEntity);
            }

            return new ServiceResult<>( chatBoxResponse,HttpStatus.OK,"Thành công");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new ServiceResult<>( null ,HttpStatus.OK,"Thất bại");
    }


}
