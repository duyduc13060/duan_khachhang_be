package com.example.du_an_demo_be.repository;

import com.example.du_an_demo_be.model.entity.RoleDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDetailRepository extends JpaRepository<RoleDetailEntity,Long> {

    List<RoleDetailEntity> findByRoleId(Long roleId);

    void deleteByRoleId(Long roleId);

}
