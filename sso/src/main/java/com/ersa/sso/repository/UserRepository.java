package com.ersa.sso.repository;

import com.ersa.sso.entity.UserBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Description
 * @Author Vanessa Yang
 * @Data 2021/1/29 0029 13:44
 */
public interface UserRepository extends JpaSpecificationExecutor<UserBean>,JpaRepository<UserBean, Integer> {

    UserBean findByUsername(String username);

}
