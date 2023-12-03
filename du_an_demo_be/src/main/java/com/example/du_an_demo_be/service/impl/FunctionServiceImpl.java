package com.example.du_an_demo_be.service.impl;

import com.example.du_an_demo_be.model.dto.FunctionsDto;
import com.example.du_an_demo_be.model.entity.ActionEntity;
import com.example.du_an_demo_be.model.entity.FuntionEntity;
import com.example.du_an_demo_be.model.entity.RoleDetailEntity;
import com.example.du_an_demo_be.model.entity.RoleEntity;
import com.example.du_an_demo_be.repository.*;
import com.example.du_an_demo_be.service.FunctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FunctionServiceImpl implements FunctionService {

    private final ActionRepository actionRepository;
    private final FuntionRepository funtionRepository;
    private final RoleRepository roleRepository;
    private final RoleDetailRepository roleDetailRepository;
    private final FuntionCustomRepository funtionCustomRepository;


    @Override
    public void create(){

        List<ActionEntity> entityList = new ArrayList<>();
        entityList.add(new ActionEntity(1L,"create", "Thêm mới"));
        entityList.add(new ActionEntity(2L,"update", "Chỉnh sửa"));
        entityList.add(new ActionEntity(3L,"delete", "Xóa"));
        entityList.add(new ActionEntity(4L,"search", "Tìm kiếm"));
        entityList.add(new ActionEntity(5L,"detail", "Chi tiết"));
        this.actionRepository.saveAll(entityList);

        List<FuntionEntity> funtionEntities = new ArrayList<>();
        funtionEntities.add(new FuntionEntity(1L,"QLU","Quản lý user"));
        funtionEntities.add(new FuntionEntity(2L,"QLN","Quản lý note"));
        funtionEntities.add(new FuntionEntity(3L,"QLTASK","Quản lý task"));
        this.funtionRepository.saveAll(funtionEntities);

        List<RoleEntity> roleEntityList = new ArrayList<>();
        roleEntityList.add(new RoleEntity(1L,"ADM","ADMIN",1,"Quản lý tất cả"));
        roleEntityList.add(new RoleEntity(2L,"USR","USER",1,"Giới hạn quyền truy cập"));
        roleEntityList.add(new RoleEntity(3L,"CTM","CUSTOMER",1,"Giới hạn quyền truy cập"));
        this.roleRepository.saveAll(roleEntityList);

        List<RoleDetailEntity> roleDetailEntities = new ArrayList<>();
        roleDetailEntities.add(new RoleDetailEntity(1L,1L,1L,"1,2,3"));
        roleDetailEntities.add(new RoleDetailEntity(2L,2L,2L,"1,2"));
        roleDetailEntities.add(new RoleDetailEntity(3L,3L,3L,"1"));
        this.roleRepository.saveAll(roleEntityList);


    }



    @Override
    public List<FunctionsDto> search() {
        return this.funtionCustomRepository.search();
    }



}
