package com.example.du_an_demo_be.controller.feign;


import com.example.du_an_demo_be.payload.request.ChatBoxRequest;
import com.example.du_an_demo_be.payload.request.LoginRequest;
import com.example.du_an_demo_be.service.ChatBoxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/chat-box")
@RequiredArgsConstructor
public class ChatBoxController {

    private final ChatBoxService chatBoxService;

    @PostMapping("/generate")
    public ResponseEntity<?> login(@RequestBody ChatBoxRequest chatBoxRequest){
       return   ResponseEntity.ok().body(chatBoxService.chatBox(chatBoxRequest));
    }

}
