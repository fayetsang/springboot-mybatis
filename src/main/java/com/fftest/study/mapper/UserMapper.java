package com.fftest.study.mapper;

import com.fftest.study.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    User findUserById(@Param("id") Long id);

    void updateUserStatus(User user);

    void deleteUserById(Long id);

    void addUser(User user);

}
