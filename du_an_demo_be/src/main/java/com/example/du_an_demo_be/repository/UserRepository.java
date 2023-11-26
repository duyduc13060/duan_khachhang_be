package com.example.du_an_demo_be.repository;

import com.example.du_an_demo_be.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);


    @Query(value = "SELECT \n" +
            "   u.id,\n" +
            "   u.address,\n" +
            "   u.create_date,\n" +
            "   u.fullname,\n" +
            "   u.phone,\n" +
            "   u.address,\n" +
            "   u.`role`,\n" +
            "   u.username\n" +
            "FROM users u\n" +
            "where ((1 = 1  \n" +
            "AND ( :username is null OR u.username = :username) \n" +
            "AND ( :fullname IS NULL OR u.fullname like(:fullname) escape '&' OR u.fullname like(:fullname) escape '&')))", nativeQuery = true)
    List<UserEntity> searchUser(String username, String fullname);

}
