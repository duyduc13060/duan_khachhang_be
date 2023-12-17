package com.example.du_an_demo_be.service;

import com.example.du_an_demo_be.model.dto.RolesDto;
import com.example.du_an_demo_be.model.entity.RoleEntity;
import com.example.du_an_demo_be.payload.response.DefaultResponse;
import com.example.du_an_demo_be.payload.response.ServiceResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {
    Page<RolesDto> search(RolesDto rolesDTO, int page, int size);

    ServiceResult delete(Long id);

    ServiceResult save(RolesDto rolesDTO);

    List<RoleEntity> getListRole();

    RolesDto getByEmployeeId(Long id) throws JsonProcessingException;
}
