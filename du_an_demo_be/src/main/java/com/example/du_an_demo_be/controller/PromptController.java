package com.example.du_an_demo_be.controller;


import com.example.du_an_demo_be.model.dto.PromptDto;
import com.example.du_an_demo_be.service.PromptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/QLPT")
@RequiredArgsConstructor
public class PromptController {

    private final PromptService promptService;

    @PostMapping("/search/prompt")
    public ResponseEntity<?> searchPrompt(
            @RequestBody PromptDto promptDto
    ){
        return ResponseEntity.ok().body(this.promptService.searchPrompt(promptDto));
    }

    @PostMapping("/create/prompt")
    public ResponseEntity<?> createPrompt(
            @RequestBody PromptDto promptDto
            ){
        return ResponseEntity.ok().body(this.promptService.createPrompt(promptDto));
    }

    @PutMapping("/update/prompt")
    public ResponseEntity<?> updatePrompt(
            @RequestBody PromptDto promptDto
    ){
        return ResponseEntity.ok().body(this.promptService.updatePrompt(promptDto));
    }

    @DeleteMapping("/delete/prompt/{idPrompt}")
    public ResponseEntity<?> deletePrompt(
            @PathVariable("idPrompt") Long idPrompt
    ){
        this.promptService.deletePrompt(idPrompt);
        return ResponseEntity.ok().body("Xóa thành công");
    }


}
