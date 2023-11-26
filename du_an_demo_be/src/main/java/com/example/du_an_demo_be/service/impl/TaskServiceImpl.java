package com.example.du_an_demo_be.service.impl;

import com.example.du_an_demo_be.model.dto.TaskDto;
import com.example.du_an_demo_be.model.entity.TaskEntity;
import com.example.du_an_demo_be.repository.TaskRepository;
import com.example.du_an_demo_be.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<TaskDto> getListTaskByUserName(String username){
        // todo:Lấy ra danh sách các công việc theo user đăng nhập
        List<TaskEntity> taskEntityList = this.taskRepository.getTaskByUsername(username);

        // todo:Chuyển đổi dữ liệu entity dang dto để view lên giao diện cho người dùng
        List<TaskDto> taskDtoList = taskEntityList
                .stream()
                .map(e -> modelMapper.map(e, TaskDto.class))
                .collect(Collectors.toList());
        return taskDtoList;
    }

}
