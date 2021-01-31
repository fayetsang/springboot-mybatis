package com.fftest.study.controller;

import com.fftest.study.pojo.User;
import com.fftest.study.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Void> addUser(User user) {
        HttpStatus status;
        try {
            if (StringUtils.isBlank(user.getName())) {
                status = HttpStatus.BAD_REQUEST;
            } else {
                userService.addUser(user);
                status = HttpStatus.CREATED;
            }
        } catch (Exception e) {
            logger.error("exception happen while addUser", e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUserById(@RequestParam("id")Long id) {
        HttpStatus status;
        try {
            if (id!=null && id>0) {
                userService.deleteUserById(id);
                status = HttpStatus.NO_CONTENT;
            } else {
                status = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception e) {
            logger.error("exception happen while deleteUserById", e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id) {
        HttpStatus status;
        User user = null;
        try {
            if (id!=null && id>0) {
                user = userService.findUserById(id);
                if (user!=null) {
                    status = HttpStatus.OK;
                } else {
                    status = HttpStatus.NOT_FOUND;
                }
            } else {
                status = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception e) {
            logger.error("exception happen while findUserById", e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).body(user);
    }

    /**
     *
     * @param user
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateUserStatus(User user) {
        HttpStatus status;
        try {
            if (StringUtils.isBlank(user.getStatus()) || user.getId() == null) {
                status = HttpStatus.BAD_REQUEST;

            } else {
                userService.updateUserStatus(user);
                status = HttpStatus.NO_CONTENT;
            }
        } catch (Exception e) {
            logger.error("exception happen while updateUserStatus", e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(status).build();
    }


}
