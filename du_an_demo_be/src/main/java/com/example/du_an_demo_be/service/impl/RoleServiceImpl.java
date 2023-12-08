package com.example.du_an_demo_be.service.impl;

import com.example.du_an_demo_be.model.dto.FunctionsDto;
import com.example.du_an_demo_be.model.dto.RolesDto;
import com.example.du_an_demo_be.model.entity.RoleDetailEntity;
import com.example.du_an_demo_be.model.entity.RoleEntity;
import com.example.du_an_demo_be.model.entity.UserEntity;
import com.example.du_an_demo_be.payload.response.ServiceResult;
import com.example.du_an_demo_be.repository.RoleCustomRepository;
import com.example.du_an_demo_be.repository.RoleDetailRepository;
import com.example.du_an_demo_be.repository.RoleRepository;
import com.example.du_an_demo_be.repository.UserRepository;
import com.example.du_an_demo_be.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleCustomRepository roleCustomRepository;
    private final UserRepository userRepository;
    private final RoleDetailRepository roleDetailRepository;
    private final ModelMapper modelMapper;


    @Override
    public Page<RolesDto> search(RolesDto rolesDTO, int page, int size) {
        return this.roleCustomRepository.search(rolesDTO,page,size);
    }

    @Override
    public ServiceResult delete(Long id) {
        ServiceResult serviceResult=new ServiceResult();

        List<UserEntity> employeesList=this.userRepository.findByRoleId(id);
        if(employeesList.size()>0){
            serviceResult.setStatus(HttpStatus.NOT_FOUND);
            serviceResult.setMessage("Role đã được gán cho user, không được xóa");
            return serviceResult;
        }
        Optional<RoleEntity> roles = this.roleRepository.findById(id);
        if(roles.isPresent()){
            roleRepository.deleteById(id);
            List<RoleDetailEntity> roleDetails = this.roleDetailRepository.findByRoleId(id);
            for(RoleDetailEntity r: roleDetails){
                roleDetailRepository.deleteById(r.getId());
            }
            serviceResult.setStatus(HttpStatus.OK);
            serviceResult.setMessage("Xoá role thành công");
        }else{
            serviceResult.setStatus(HttpStatus.NOT_FOUND);
            serviceResult.setMessage("Role không tồn tại");
        }

        return serviceResult;
    }

    @Transactional
    @Override
    public ServiceResult save(RolesDto rolesDTO) {
        ServiceResult serviceResult=new ServiceResult<>();
//        Optional<UserEntity> employees = this.employeesService.getUserWithAuthorities();

        if(rolesDTO.getId()==null){
            // trung code
            if(this.roleRepository.findByCode(rolesDTO.getCode()).isPresent()){
                serviceResult.setStatus(HttpStatus.NOT_FOUND);
                serviceResult.setData(rolesDTO);
                serviceResult.setMessage("Mã đã tồn tại");
                return serviceResult;
            }
            // save roles
            rolesDTO.setCode(rolesDTO.getCode().toUpperCase());
//            rolesDTO.setCreateTime(Instant.now());
//            rolesDTO.setUpdateTime(Instant.now());
            RoleEntity roles = modelMapper.map(rolesDTO,RoleEntity.class);
            this.roleRepository.save(roles);

            // save roleDetails
            RoleEntity rolesCreate = this.roleRepository.findByCode(rolesDTO.getCode()).get();
            for(FunctionsDto f: rolesDTO.getListFunctions()){
                if(f.isSelected()){
                    if(f.getListActionSelected().size()>0){
                        RoleDetailEntity roleDetails = new RoleDetailEntity();
                        roleDetails.setRoleId(rolesCreate.getId());
                        roleDetails.setFunctionId(f.getId());
                        roleDetails.setAction(StringUtils.join(f.getListActionSelected(), ","));
                        this.roleDetailRepository.save(roleDetails);
                    }
                }
            }

            serviceResult.setStatus(HttpStatus.OK);
            serviceResult.setData(rolesDTO);
            serviceResult.setMessage("Thêm mới role thành công");
        }else{
            RoleEntity rolesUpdate = this.roleRepository.findByCode(rolesDTO.getCode()).get();

            // save roles
            rolesDTO.setCode(rolesDTO.getCode().toUpperCase());
//            rolesDTO.setCreateTime(rolesUpdate.getCreateTime());
//            rolesDTO.setUpdateTime(Instant.now());
            RoleEntity roles =  modelMapper.map(rolesDTO,RoleEntity.class);
            this.roleRepository.save(roles);

            this.roleDetailRepository.deleteByRoleId(rolesUpdate.getId());
            // save roleDetails
            for(FunctionsDto f: rolesDTO.getListFunctions()){
                if(f.isSelected()){
                    if(f.getListActionSelected().size()>0){
                        RoleDetailEntity roleDetails = new RoleDetailEntity();

                        roleDetails.setRoleId(rolesUpdate.getId());
                        roleDetails.setFunctionId(f.getId());
                        roleDetails.setAction(StringUtils.join(f.getListActionSelected(), ","));
                        this.roleDetailRepository.save(roleDetails);
                    }
                }
            }

            serviceResult.setStatus(HttpStatus.OK);
            serviceResult.setData(rolesDTO);
            serviceResult.setMessage("Cập nhật role thành công");
        }

        return serviceResult;
    }

    @Override
    public List<RoleEntity> getListRole(){
        return roleRepository.findAll();
    }

}
