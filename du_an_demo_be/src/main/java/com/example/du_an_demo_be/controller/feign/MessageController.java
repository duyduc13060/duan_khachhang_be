package com.example.du_an_demo_be.controller.feign;


import com.example.du_an_demo_be.payload.request.ChatBoxGeminiProRequest;
import com.example.du_an_demo_be.payload.request.ChatBoxRequest;
import com.example.du_an_demo_be.payload.request.amazon.ChatBoxAmazonRequest;
import com.example.du_an_demo_be.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/QLCHAT/chat-box")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/get-message")
    public ResponseEntity<?> getMessage(
            @RequestParam(value = "type") Integer type
    ){
        return ResponseEntity.ok().body(messageService.getListMessage(type));
    }

//    @PostMapping("/generate-message")
//    public ResponseEntity<?> sendMessage(@RequestBody ChatBoxRequest chatBoxRequest){
//       return   ResponseEntity.ok().body(messageService.saveMessage(chatBoxRequest));
//    }

    @PostMapping("/generate-message")
    public ResponseEntity<?> sendMessage(@RequestBody ChatBoxRequest chatBoxRequest){
       return   ResponseEntity.ok().body(messageService.saveMessageRestTemplate(chatBoxRequest));
    }


    //Todo:
    @PostMapping("/generate-message-amazon")
    public ResponseEntity<?> sendMessage(@RequestBody ChatBoxAmazonRequest chatBoxRequest){
        return   ResponseEntity.ok().body(messageService.saveMessageChatBoxAmazon(chatBoxRequest));
    }

    @PostMapping("/generate-message-gemini-pro")
    public ResponseEntity<?> sendMessage(@RequestBody ChatBoxGeminiProRequest chatBoxRequest){
        return   ResponseEntity.ok().body(messageService.saveMessageChatBoxGeminiPro(chatBoxRequest));
    }

}
