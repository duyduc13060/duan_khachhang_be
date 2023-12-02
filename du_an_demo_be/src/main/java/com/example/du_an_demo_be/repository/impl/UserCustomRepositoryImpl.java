package com.example.du_an_demo_be.repository.impl;

import com.example.du_an_demo_be.common.DataUtil;
import com.example.du_an_demo_be.model.dto.UserDto;
import com.example.du_an_demo_be.repository.UserCustomRepository;
import com.example.du_an_demo_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final UserRepository userRepository;
    private final EntityManager entityManager;

    @Override
    public List<UserDto> searchUser(UserDto userDto){

        StringBuilder sql = new StringBuilder("SELECT \n" +
                "   u.id,\n" +
                "   u.address,\n" +
                "   u.create_date createDate,\n" +
                "   u.fullname,\n" +
                "   u.phone,\n" +
                "   u.`role`,\n" +
                "   u.username,\n" +
                "   u.status,\n" +
                "   u.email\n" +
                "FROM users u\n" +
                "where (\t(1 = 1  \n" +
                "\t\tAND ( :status is null or u.status = :status)\n" +
                "\t\tAND ( :keySearch is null or u.username like CONCAT('%', :keySearch, '%') OR u.fullname like CONCAT('%', :keySearch, '%'))\t\t\n" +
                "))");

        Query query = entityManager.createNativeQuery(sql.toString());
        query.setParameter("status", userDto.getStatus());
        query.setParameter("keySearch", userDto.getKeySearch());

        List<UserDto> userDtoList = new ArrayList<>();
        List<Object[]> objects = query.getResultList();
        if (ObjectUtils.isNotEmpty(objects)) {
            for (Object[] obj : objects) {
                UserDto userDto1 = new UserDto();

                userDto1.setId(DataUtil.safeToLong(obj[0]));
                userDto1.setAddress(DataUtil.safeToString(obj[1]));
                userDto1.setCreateDate(DataUtil.safeToLocalDateTime(obj[2]));
                userDto1.setFullname(DataUtil.safeToString(obj[3]));
                userDto1.setPhone(DataUtil.safeToString(obj[4]));
                userDto1.setRole(DataUtil.safeToInt(obj[5]));
                userDto1.setUsername(DataUtil.safeToString(obj[6]));
                userDto1.setStatus(DataUtil.safeToInt(obj[7]));
                userDto1.setEmail(DataUtil.safeToString(obj[8]));
                userDtoList.add(userDto1);
            }
        }
        return userDtoList;
    }


}
