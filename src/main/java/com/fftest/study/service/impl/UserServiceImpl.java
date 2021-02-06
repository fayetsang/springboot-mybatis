package com.fftest.study.service.impl;

import com.fftest.study.mapper.UserMapper;
import com.fftest.study.pojo.User;
import com.fftest.study.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserById(Long id) {
        return userMapper.findUserById(id);
    }

    @Override
    public void updateUserStatus(User user){
        userMapper.updateUserStatus(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userMapper.deleteUserById(id);
    }

    @Override
    public void addUser(User user) {
        if (log.isDebugEnabled()) {
            log.debug("enter addUser, user: {}", user.toString());
        }

        if (StringUtils.isBlank(user.getStatus())) {
            if (log.isDebugEnabled()) {
                log.debug("status is null, set status to inactive");
            }
            user.setStatus("inactive");
        }
        userMapper.addUser(user);
    }
}
