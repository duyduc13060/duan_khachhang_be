package com.example.du_an_demo_be.repository;

import com.example.du_an_demo_be.model.dto.RolesDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleCustomRepository {
    Page<RolesDto> search(RolesDto rolesDTO, int page, int size);

    RolesDto getByEmployeeId(Long id) throws JsonProcessingException;
}
