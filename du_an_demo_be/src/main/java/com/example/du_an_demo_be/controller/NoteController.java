package com.example.du_an_demo_be.controller;

import com.example.du_an_demo_be.model.dto.UserDto;
import com.example.du_an_demo_be.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/QLNOTE/note")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping("/get-list-note-by-user")
    public ResponseEntity<?> getNoteByUser(
            @RequestBody UserDto user
    ){
        return ResponseEntity.ok().body(this.noteService.getNoteByUser(user.getUsername()));
    }

}
