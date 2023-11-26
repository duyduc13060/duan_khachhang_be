package com.example.du_an_demo_be.service;

import com.example.du_an_demo_be.model.dto.TaskDto;

import java.util.List;

public interface TaskService {
    List<TaskDto> getListTaskByUserName(String username);
}
