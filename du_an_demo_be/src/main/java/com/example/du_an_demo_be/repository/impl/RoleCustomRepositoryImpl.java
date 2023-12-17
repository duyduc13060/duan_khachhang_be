package com.example.du_an_demo_be.repository.impl;

import com.example.du_an_demo_be.common.DataUtil;
import com.example.du_an_demo_be.common.StringUtils;
import com.example.du_an_demo_be.model.dto.ActionsDto;
import com.example.du_an_demo_be.model.dto.FunctionsDto;
import com.example.du_an_demo_be.model.dto.RolesDto;
import com.example.du_an_demo_be.model.entity.ActionEntity;
import com.example.du_an_demo_be.repository.ActionRepository;
import com.example.du_an_demo_be.repository.RoleCustomRepository;
import com.example.du_an_demo_be.repository.RoleRepository;
import com.example.du_an_demo_be.service.FunctionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleCustomRepositoryImpl implements RoleCustomRepository {

    private final RoleRepository roleRepository;
    private final EntityManager entityManager;
    private final FunctionService functionService;
    private final ActionRepository actionRepository;
    private final ModelMapper modelMapper;

    private static final String STATUS_PARAM = "status";
    private static final String TEXT_SEARCH_PARAM = "textSearch";


    @Override
    public Page<RolesDto> search(RolesDto rolesDTO, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<RolesDto> list=new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT t1.*, t2.count_user\n" +
                "FROM (\n" +
                "    SELECT r.*, GROUP_CONCAT(rd.function_id ORDER BY f.name SEPARATOR '|') AS function_id, GROUP_CONCAT(rd.`action` ORDER BY f.name SEPARATOR '|') as action\n" +
                "    FROM roles r\n" +
                "    RIGHT JOIN role_details rd ON rd.role_id = r.id\n" +
                "    JOIN functions f ON f.id = rd.function_id\n" +
                "    WHERE TRUE ");
        if(StringUtils.isNotNullOrEmpty(rolesDTO.getTextSearch())){
            sql.append(" and (BINARY(UPPER(r.code)) like BINARY(UPPER(:textSearch)) or BINARY(UPPER(r.name)) like BINARY(UPPER(:textSearch))) ");
        }
        if (Objects.nonNull(rolesDTO.getStatus())) {
            sql.append(" and r.status = :status ");
        }
        sql.append(" GROUP by r.id,rd.role_id ");
        sql.append(" ) t1\n" +
                "JOIN (\n" +
                "    SELECT r2.id, COUNT(e.role_id) AS count_user\n" +
                "    FROM roles r2\n" +
                "    LEFT JOIN users e ON e.role_id = r2.id\n" +
                "    GROUP BY r2.id\n" +
                ") t2 ON t1.id = t2.id ");

        if(StringUtils.isNotNullOrEmpty(rolesDTO.getOrder()) && StringUtils.isNotNullOrEmpty(rolesDTO.getOrderName())){
            if(rolesDTO.getOrderName().equals("updateTime")){
                sql.append(" order by t1.update_time " + rolesDTO.getOrder() + " ");
            }else{
                sql.append(" order by t1." + rolesDTO.getOrderName() + " "+ rolesDTO.getOrder() + " ");
            }
        }else{
            sql.append(" order by t1.code asc ");
        }

        Query query = entityManager.createNativeQuery(sql.toString());
        Query countQuery = entityManager.createNativeQuery("SELECT COUNT(*) FROM (" + sql + ") as x");
        if(StringUtils.isNotNullOrEmpty(rolesDTO.getTextSearch())){
            String textSearch = rolesDTO.getTextSearch();
            if(textSearch.contains("%")){
                textSearch = textSearch.replace("%","\\%");
            }
            if(textSearch.contains("_")){
                textSearch = textSearch.replace("_","\\_");
            }
            query.setParameter(TEXT_SEARCH_PARAM, "%" + textSearch + "%");
            countQuery.setParameter(TEXT_SEARCH_PARAM, "%" + textSearch + "%");
        }
        if (Objects.nonNull(rolesDTO.getStatus())) {
            query.setParameter(STATUS_PARAM, rolesDTO.getStatus());
            countQuery.setParameter(STATUS_PARAM, rolesDTO.getStatus());
        }

        long countResult = ((BigInteger) countQuery.getSingleResult()).longValue();

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<Object[]> lstObj = query.getResultList();

        if (lstObj != null && !lstObj.isEmpty()) {
            for (Object[] obj : lstObj) {
                RolesDto dto = new RolesDto();

                dto.setId(DataUtil.safeToLong(obj[0]));
                dto.setCode(DataUtil.safeToString(obj[1]));
                dto.setCreateTime(DataUtil.safeToLocalDateTime(obj[2]));
                dto.setName(DataUtil.safeToString(obj[3]));
                dto.setStatus(DataUtil.safeToLong(obj[4]));
                dto.setUpdateTime(DataUtil.safeToLocalDateTime(obj[5]));
                dto.setDescription(DataUtil.safeToString(obj[6]));
                dto.setFunctionId(DataUtil.safeToString(obj[7]));
                dto.setAction(DataUtil.safeToString(obj[8]));
                dto.setCountUser(DataUtil.safeToLong(obj[9]));

                list.add(dto);
            }
        }

        // set list functions
        for(RolesDto r: list){
            List<FunctionsDto> functionsDTOList=this.functionService.search();
            for(FunctionsDto f: functionsDTOList){
                f.setSelectedAll(false);
                if(r.getFunctionId().contains("|")){
                    List<String> listFunc = this.splitString(r.getFunctionId());
                    List<String> listAction = this.splitString(r.getAction());
                    for(int i=0;i<listFunc.size();i++){
                        if(f.getId()==Long.parseLong(listFunc.get(i))){
                            f.setSelected(true);
                            f.setListActionSelected(this.stringToList(listAction.get(i)));
                            if(f.getListActionSelected().size()==f.getListActions().size()){
                                f.setSelectedAll(true);
                            }
                        }
                    }
                }else{
                    if(f.getId()==Long.parseLong(r.getFunctionId())){
                        f.setSelected(true);
                        f.setListActionSelected(this.stringToList(r.getAction()));
                        if(f.getListActionSelected().size()==f.getListActions().size()){
                            f.setSelectedAll(true);
                        }
                    }
                }
            }
            r.setListFunctions(functionsDTOList);
        }

        return new PageImpl<>(list, pageable, countResult);
    }

    List<String> splitString(String str){
        String[] result = str.split("\\|");
        List<String> lst = new ArrayList<>();
        for(String a : result){
            lst.add(a);
        }
        return lst;
    }

    List<Integer> stringToList(String str){
        String[] result = str.split("\\,");
        List<Integer> lst = new ArrayList<>();
        for(String a : result){
            lst.add(Integer.parseInt(a));
        }
        return lst;
    }

    @Override
    public RolesDto getByEmployeeId(Long id) throws JsonProcessingException {
        RolesDto dto=new RolesDto();

        StringBuilder sql = new StringBuilder("select r.code,r.name,r.status,r.description,\n" +
                "GROUP_CONCAT(rd.function_id ORDER BY f.name SEPARATOR '|'),\n" +
                "GROUP_CONCAT(f.code ORDER BY f.name SEPARATOR '|'),\n" +
                "GROUP_CONCAT(rd.`action` ORDER BY f.name SEPARATOR '|') \n" +
                "FROM roles r \n" +
                "right join role_details rd on rd.role_id = r.id \n" +
                "join functions f on f.id = rd.function_id\n" +
                "join users u on u.role_id = r.id \n" +
                "WHERE u.id = :userId\n" +
                "GROUP by r.id,rd.role_id");
        Query query = entityManager.createNativeQuery(sql.toString());

        if (Objects.nonNull(id)){
            query.setParameter("userId", id);
        }

        List<Object[]> lstObj = query.getResultList();
        if (lstObj != null && !lstObj.isEmpty()) {
            Object[] obj = lstObj.get(0);

            dto.setCode(DataUtil.safeToString(obj[0]));
            dto.setName(DataUtil.safeToString(obj[1]));
            dto.setStatus(DataUtil.safeToLong(obj[2]));
            dto.setDescription(DataUtil.safeToString(obj[3]));
            dto.setFunctionId(DataUtil.safeToString(obj[4]));
            dto.setFunctionCode(DataUtil.safeToString(obj[5]));
            dto.setAction(DataUtil.safeToString(obj[6]));
        }

        List<FunctionsDto> functionsDTOList = new ArrayList<>();
        List<String> functionActions = new ArrayList<>();
        if(dto.getFunctionId().contains("|")){
            List<String> listFunc = this.splitString(dto.getFunctionId());
            List<String> listFuncCode = this.splitString(dto.getFunctionCode());
            List<String> listAction = this.splitString(dto.getAction());
            for(int i=0;i<listFunc.size();i++){
                FunctionsDto functionsDTO = new FunctionsDto();
                functionsDTO.setId(Long.parseLong(listFunc.get(i)));
                functionsDTO.setCode(listFuncCode.get(i));
//                functionsDTO.setListActionSelected(this.stringToList(listAction.get(i)));
                functionsDTO.setListActions(this.stringToListAction(listAction.get(i)));

                functionsDTOList.add(functionsDTO);
                this.stringFunctionAction(functionActions,functionsDTO.getCode(),listAction.get(i));
            }
        }else{
            FunctionsDto functionsDTO = new FunctionsDto();
            functionsDTO.setId(Long.parseLong(dto.getFunctionId()));
            functionsDTO.setCode(dto.getFunctionCode());
//            functionsDTO.setListActionSelected(this.stringToList(dto.getAction()));
            functionsDTO.setListActions(this.stringToListAction(dto.getAction()));

            functionsDTOList.add(functionsDTO);
            this.stringFunctionAction(functionActions,functionsDTO.getCode(),dto.getAction());
        }
        dto.setFunctionAction(functionActions);
        dto.setListFunctions(functionsDTOList);

        return dto;
    }


    List<ActionsDto> stringToListAction(String str){
        List<ActionsDto> list=new ArrayList<>();
        String[] result = str.split("\\,");
        for(String a : result){
            Optional<ActionEntity> actions = this.actionRepository.findById(Long.parseLong(a));
            if(actions.isPresent()){
                ActionsDto actionDTO = modelMapper.map(actions.get(), ActionsDto.class);
                list.add(actionDTO);
            }
        }
        return list;
    }

    void stringFunctionAction(List<String> list, String functionCode,String str){
        String[] result = str.split("\\,");
        for(String a : result){
            Optional<ActionEntity> actions = this.actionRepository.findById(Long.parseLong(a));
            if(actions.isPresent()){
                list.add(functionCode +"-"+actions.get().getCode());
            }
        }
    }


}
