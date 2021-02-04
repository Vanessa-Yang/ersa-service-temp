package com.ersa.sso.service;

import com.ersa.sso.Entity.UserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author Vanessa Yang
 * @Data 2021/1/29 0029 13:40
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserBean getByUsername(String username) {
        return null;
    }
}
