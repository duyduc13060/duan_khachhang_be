package com.example.du_an_demo_be.service.impl;

import com.example.du_an_demo_be.payload.request.ChatBoxGeminiProRequest;
import com.example.du_an_demo_be.payload.request.ChatBoxRequest;
import com.example.du_an_demo_be.payload.request.amazon.ChatBoxAmazonRequest;
import com.example.du_an_demo_be.payload.response.ChatBoxResponse;
import com.example.du_an_demo_be.payload.response.DefaultResponse;
import com.example.du_an_demo_be.payload.response.ResultApiChatBox;
import com.example.du_an_demo_be.payload.response.amazon.ChatBoxAmazonResponse;
import com.example.du_an_demo_be.service.ChatBoxService;
//import okhttp3.MediaType;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.http.MediaType;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class ChatBoxServiceImpl implements ChatBoxService {


    private MultiValueMap<String, String> getHeaders(String token) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("accept", "application/json");
        headers.add("authorization", "Bearer " + token);

        return headers;
    }

    private MultiValueMap<String, String> getHeaders() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("content-type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("accept", "application/json");
        return headers;
    }


    @Override
    public DefaultResponse<ResultApiChatBox> chatBox(ChatBoxRequest chatBoxRequest, String api, String token){

        DefaultResponse<ResultApiChatBox> resultApiChatBoxDefaultResponse = new DefaultResponse<>();
        ResultApiChatBox resultApiChatBox = new ResultApiChatBox();

//        JSONObject jsonObject = new JSONObject();
//        OkHttpClient client = new OkHttpClient();
//
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, new Gson().toJson(chatBoxRequest));
//
//        Request request = new Request.Builder()
//                .url(api)
//                .post(body)
//                .addHeader("accept", "application/json")
//                .addHeader("content-type", "application/json")
//                .addHeader("authorization", "Bearer " + token)
//                .build();
//
//        try {
//            Response response = client.newCall(request).execute();
//            // Xử lý dữ liệu response ở đây
//            String responseBody = response.body().string();
//            jsonObject = new JSONObject(Objects.requireNonNull(responseBody));
//            resultApiChatBox.setJsonObject(jsonObject);
//            resultApiChatBox.setJsonObjectString(responseBody);
//        } catch (Exception e) {
//            e.printStackTrace();
//            resultApiChatBoxDefaultResponse.setSuccess(HttpStatus.BAD_REQUEST.value());
//        }
//
//        resultApiChatBoxDefaultResponse.setSuccess(HttpStatus.OK.value());
//        resultApiChatBoxDefaultResponse.setData(resultApiChatBox);

        return resultApiChatBoxDefaultResponse;
    }



    @Override
    public DefaultResponse<ResultApiChatBox> chatBoxCallRestTemplate(ChatBoxRequest chatBoxRequest, String UrlApi, String token){

        DefaultResponse<ResultApiChatBox> resultApiChatBoxDefaultResponse = new DefaultResponse<>();
        ResultApiChatBox resultApiChatBox = new ResultApiChatBox();

//        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
//
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.53.120.10", 8080));
//        requestFactory.setProxy(proxy);

//        RestTemplate restTemplate = new RestTemplate(requestFactory);

          RestTemplate restTemplate = new RestTemplate();


//          RestTemplate restTemplate = new RestTemplate();


        try {
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        } catch (
                UnsupportedOperationException | ClassCastException | NullPointerException | IllegalArgumentException |
                IllegalStateException e
        ) {
            resultApiChatBoxDefaultResponse.setSuccess(HttpStatus.BAD_REQUEST.value());
            resultApiChatBoxDefaultResponse.setMessage(e.getMessage());
            return resultApiChatBoxDefaultResponse;
        }

        MultiValueMap<String, String> headers = this.getHeaders(token);

        HttpEntity<Object> request = new HttpEntity<>(chatBoxRequest,headers);
        JSONObject jsonObject;
        try {
            ResponseEntity<ChatBoxResponse> responseEntity = restTemplate.exchange(
                    UrlApi,
                    HttpMethod.POST,
                    request,
                    ChatBoxResponse.class);

            jsonObject = new JSONObject(Objects.requireNonNull(responseEntity.getBody()));
            String responseBody = responseEntity.getBody().toString();

            resultApiChatBox.setJsonObject(jsonObject);
            resultApiChatBox.setJsonObjectString(responseBody);
        }catch (final HttpClientErrorException ex) {
            resultApiChatBoxDefaultResponse.setSuccess(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resultApiChatBoxDefaultResponse.setMessage("Thất bại");
        }

        resultApiChatBoxDefaultResponse.setSuccess(HttpStatus.OK.value());
        resultApiChatBoxDefaultResponse.setData(resultApiChatBox);
        return resultApiChatBoxDefaultResponse;
    }


    @Override
    public DefaultResponse<ResultApiChatBox> chatBoxAmazon(ChatBoxAmazonRequest chatBoxRequest, String UrlApi){
        DefaultResponse<ResultApiChatBox> resultApiChatBoxDefaultResponse = new DefaultResponse<>();
        ResultApiChatBox resultApiChatBox = new ResultApiChatBox();

                SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.53.120.10", 8080));
        requestFactory.setProxy(proxy);
        RestTemplate restTemplate = new RestTemplate(requestFactory);

//        RestTemplate restTemplate = new RestTemplate();

        try {
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        } catch (
                UnsupportedOperationException | ClassCastException | NullPointerException | IllegalArgumentException |
                IllegalStateException e
        ) {
            resultApiChatBoxDefaultResponse.setSuccess(HttpStatus.BAD_REQUEST.value());
            resultApiChatBoxDefaultResponse.setMessage(e.getMessage());
            return resultApiChatBoxDefaultResponse;
        }

        MultiValueMap<String, String> headers = this.getHeaders();
        HttpEntity<Object> request = new HttpEntity<>(  chatBoxRequest,headers);
        JSONObject jsonObject;

        try {
            ResponseEntity<ChatBoxAmazonResponse> responseEntity = restTemplate.exchange(
                    UrlApi,
                    HttpMethod.POST,
                    request,
                    ChatBoxAmazonResponse.class);

            jsonObject = new JSONObject(Objects.requireNonNull(responseEntity.getBody()));
            String responseBody = responseEntity.getBody().toString();

            resultApiChatBox.setJsonObject(jsonObject);
            resultApiChatBox.setJsonObjectString(responseBody);
        }catch (final HttpClientErrorException ex) {
            resultApiChatBoxDefaultResponse.setSuccess(HttpStatus.INTERNAL_SERVER_ERROR.value());
            resultApiChatBoxDefaultResponse.setMessage("Thất bại");
        }

        resultApiChatBoxDefaultResponse.setSuccess(HttpStatus.OK.value());
        resultApiChatBoxDefaultResponse.setData(resultApiChatBox);
        return resultApiChatBoxDefaultResponse;

    }

    @Override
    public DefaultResponse<ResultApiChatBox> chatBoxTest(ChatBoxAmazonRequest chatBoxRequest, String UrlApi){

        DefaultResponse<ResultApiChatBox> resultApiChatBoxDefaultResponse = new DefaultResponse<>();
        ResultApiChatBox resultApiChatBox = new ResultApiChatBox();

        JSONObject jsonObject = new JSONObject();

        // todo: đoạn code này dùng để set proxy
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.53.120.10", 8080));
        requestFactory.setProxy(proxy);

        OkHttpClient.Builder builder = new OkHttpClient.Builder().proxy(proxy)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .callTimeout(60, TimeUnit.SECONDS);
        OkHttpClient client = builder.build();

//        OkHttpClient client = new OkHttpClient();

        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, new Gson().toJson(chatBoxRequest));

        Request request = new Request.Builder()
                .url(UrlApi)
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
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

    @Override
    public DefaultResponse<ResultApiChatBox> chatBoxGeminiPro(ChatBoxGeminiProRequest chatBoxRequest, String UrlApi){

        DefaultResponse<ResultApiChatBox> resultApiChatBoxDefaultResponse = new DefaultResponse<>();
        ResultApiChatBox resultApiChatBox = new ResultApiChatBox();

        JSONObject jsonObject = new JSONObject();

        // todo: đoạn code này dùng để set proxy
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.53.120.10", 8080));
        requestFactory.setProxy(proxy);

        OkHttpClient.Builder builder = new OkHttpClient.Builder().proxy(proxy)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .callTimeout(60, TimeUnit.SECONDS);
        OkHttpClient client = builder.build();

//        OkHttpClient client = new OkHttpClient();

        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, new Gson().toJson(chatBoxRequest));

        Request request = new Request.Builder()
                .url(UrlApi)
                .post(body)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
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
