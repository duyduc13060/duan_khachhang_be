package com.example.du_an_demo_be.repository.impl;

import com.example.du_an_demo_be.common.DataUtil;
import com.example.du_an_demo_be.model.dto.ActionsDto;
import com.example.du_an_demo_be.model.dto.FunctionsDto;
import com.example.du_an_demo_be.model.entity.ActionEntity;
import com.example.du_an_demo_be.repository.ActionRepository;
import com.example.du_an_demo_be.repository.FuntionCustomRepository;
import com.example.du_an_demo_be.repository.FuntionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FunctionCustomRepositoryImpl implements FuntionCustomRepository {

    private final FuntionRepository funtionRepository;
    private final EntityManager entityManager;
    private final ActionRepository actionsRepository;


    @Override
    public List<FunctionsDto> search() {
        List<FunctionsDto> list = new ArrayList<>();

        // new list action
        List<ActionsDto> listActionsEmpty = new ArrayList<>();
        List<ActionEntity> actionsDTOList = this.actionsRepository.findAll();
        for(ActionEntity a: actionsDTOList){
            ActionsDto actionsDTO = new ActionsDto();

            actionsDTO.setId(a.getId());
            actionsDTO.setCode(a.getCode());
            actionsDTO.setName(a.getName());

            listActionsEmpty.add(actionsDTO);
        }

        StringBuilder sql = new StringBuilder("select * from functions f order by f.name asc");
        Query query = entityManager.createNativeQuery(sql.toString());

        List<Object[]> lstObj = query.getResultList();

        if (lstObj != null && !lstObj.isEmpty()) {
            for (Object[] obj : lstObj) {
                FunctionsDto dto=new FunctionsDto();

                dto.setId(DataUtil.safeToLong(obj[0]));
                dto.setCode(DataUtil.safeToString(obj[1]));
                dto.setName(DataUtil.safeToString(obj[2]));
                dto.setSelected(false);
                dto.setSelectedAll(false);
                dto.setListActionSelected(new ArrayList<>());
                dto.setListActions(listActionsEmpty);

                list.add(dto);
            }
        }

        return list;
    }

}
