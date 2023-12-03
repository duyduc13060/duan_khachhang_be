package com.example.du_an_demo_be.service.impl;

import com.example.du_an_demo_be.payload.request.ChatBoxRequest;
import com.example.du_an_demo_be.payload.response.DefaultResponse;
import com.example.du_an_demo_be.payload.response.ResultApiChatBox;
import com.example.du_an_demo_be.service.ChatBoxService;
import okhttp3.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;

import java.util.Objects;

@Service
public class ChatBoxServiceImpl implements ChatBoxService {

    @Value("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")
    public String token;

    @Value("https://api.perplexity.ai/chat/completions")
    public String apiChatBox;




//    public static void main(String[] args) {
//
//        ChatBoxRequest.Messages messages = new ChatBoxRequest.Messages();
//        messages.setRole("system");
//        messages.setContent("Be precise and concise.");
//
//        ChatBoxRequest.Messages messages1 = new ChatBoxRequest.Messages();
//        messages1.setRole("user");
//        messages1.setContent("trong java spring boot có bảo mật không ?");
//
//        List<ChatBoxRequest.Messages> messages2 = new ArrayList<>();
//        messages2.add(messages);
//        messages2.add(messages1);
//
//
//        ChatBoxRequest chatBoxRequest = ChatBoxRequest.builder()
//                .model("mistral-7b-instruct")
//                .messages(messages2)
//                .build();
//
//
//        HttpEntity<Object> requestEntity = new HttpEntity<>(chatBoxRequest);
//
//        chatBox(requestEntity);
//    }

    @Override
    public DefaultResponse<ResultApiChatBox> chatBox(ChatBoxRequest chatBoxRequest){

        DefaultResponse<ResultApiChatBox> resultApiChatBoxDefaultResponse = new DefaultResponse<>();
        ResultApiChatBox resultApiChatBox = new ResultApiChatBox();

        JSONObject jsonObject = new JSONObject();
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, new Gson().toJson(chatBoxRequest));

        Request request = new Request.Builder()
                .url(apiChatBox)
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer " + token)
                .build();

        try {
            Response response = client.newCall(request).execute();
            // Xử lý dữ liệu response ở đây
            String responseBody = response.body().string();
            jsonObject = new JSONObject(Objects.requireNonNull(responseBody));
            resultApiChatBox.setJsonObject(jsonObject);
            resultApiChatBox.setJsonObjectString(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
            resultApiChatBoxDefaultResponse.setSuccess(HttpStatus.BAD_REQUEST.value());
        }

        resultApiChatBoxDefaultResponse.setData(resultApiChatBox);

        return resultApiChatBoxDefaultResponse;
    }



}
