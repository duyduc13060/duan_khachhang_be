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


    @Override
    public DefaultResponse<ResultApiChatBox> chatBox(ChatBoxRequest chatBoxRequest, String api, String token){

        DefaultResponse<ResultApiChatBox> resultApiChatBoxDefaultResponse = new DefaultResponse<>();
        ResultApiChatBox resultApiChatBox = new ResultApiChatBox();

        JSONObject jsonObject = new JSONObject();
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, new Gson().toJson(chatBoxRequest));

        Request request = new Request.Builder()
                .url(api)
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

        resultApiChatBoxDefaultResponse.setSuccess(HttpStatus.OK.value());
        resultApiChatBoxDefaultResponse.setData(resultApiChatBox);

        return resultApiChatBoxDefaultResponse;
    }



}
