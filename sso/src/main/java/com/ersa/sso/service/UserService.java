package com.ersa.sso.service;

import com.ersa.sso.entity.UserBean;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @Author Vanessa Yang
 * @Data 2021/1/29 0029 11:29
 */
@Service
public interface UserService {

    UserBean getByUsername(String username);

    List<UserBean> getByOpenId(String openId);

    UserBean addUser(UserBean userBean);

    UserBean queryUserById(Integer id);
}
