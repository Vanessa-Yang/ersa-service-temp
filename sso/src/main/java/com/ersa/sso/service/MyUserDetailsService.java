package com.ersa.sso.service;

import com.alibaba.fastjson.JSON;
import com.ersa.sso.Entity.UserBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author Vanessa Yang
 * @Data 2021/1/29 0029 11:26
 */
@Slf4j
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserBean user = userService.getByUsername(username);
        if(null == user){
            log.warn("用户{}不存在", username);
            throw new UsernameNotFoundException(username);
        }
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("User"));
        User user1 = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()), authorityList);

        log.info("登录成功！用户：{}", JSON.toJSONString(user1));
        return user1;
    }
}
