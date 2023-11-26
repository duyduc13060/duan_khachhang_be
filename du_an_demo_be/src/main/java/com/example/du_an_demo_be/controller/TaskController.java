package com.example.du_an_demo_be.controller;

import com.example.du_an_demo_be.model.dto.UserDto;
import com.example.du_an_demo_be.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/get-list-task-by-user")
    public ResponseEntity<?> getListTaskByUserName(
            @RequestBody UserDto user
    ){
        return ResponseEntity.ok().body(this.taskService.getListTaskByUserName(user.getUsername()));
    }

}
