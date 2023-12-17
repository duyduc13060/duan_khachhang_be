package com.example.du_an_demo_be.controller;

import com.example.du_an_demo_be.model.dto.RolesDto;
import com.example.du_an_demo_be.payload.response.DefaultResponse;
import com.example.du_an_demo_be.service.FunctionService;
import com.example.du_an_demo_be.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/QLR")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;
    private final FunctionService functionService;

    @PostMapping("/search/role/search/roles/search")
    public Page<RolesDto> search(@RequestBody RolesDto rolesDTO,
                                 @RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "size", defaultValue = "10") int size){
        return this.roleService.search(rolesDTO,page,size);
    }

    @PostMapping("/create/role/create/roles")
    public ResponseEntity<?> createRoles(@RequestBody RolesDto rolesDTO) throws URISyntaxException {
        return ResponseEntity.ok(this.roleService.save(rolesDTO));
    }

    @PostMapping("/update/role/update/roles")
    public ResponseEntity<?> updateRoles(@RequestBody RolesDto rolesDTO) throws URISyntaxException {
        return ResponseEntity.ok(this.roleService.save(rolesDTO));
    }

    @PostMapping("/delete/role/delete/roles")
    public ResponseEntity<?> deleteRoles(@RequestBody RolesDto rolesDTO) {
        return ResponseEntity.ok(this.roleService.delete(rolesDTO.getId()));
    }

    @GetMapping("/role/list/get-all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(roleService.getListRole());
    }

}
