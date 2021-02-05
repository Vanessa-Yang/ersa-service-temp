package com.ersa.sso.controller;

import com.ersa.sso.entity.UserBean;
import com.ersa.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description
 * @Author Vanessa Yang
 * @Data 2021/2/5 0005 13:52
 */
@RestController
@RequestMapping("/user")
public class UserApi {

    @Autowired
    private UserService userService;

    @PostMapping("/queryUserById/{id}")
    public UserBean queryUserById(@PathVariable Integer id){
        UserBean user = userService.queryUserById(id);
        return user;
    }
}
