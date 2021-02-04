package com.ersa.sso.service;

import com.ersa.sso.entity.UserBean;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author Vanessa Yang
 * @Data 2021/1/29 0029 11:29
 */
@Service
public interface UserService {

    UserBean getByUsername(String username);

}
