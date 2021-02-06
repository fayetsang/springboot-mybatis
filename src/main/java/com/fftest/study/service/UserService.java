package com.fftest.study.service;

import com.fftest.study.pojo.User;


public interface UserService {

    User findUserById(Long id);

    void updateUserStatus(User user);

    void deleteUserById(Long id);

    void addUser(User user);
}
