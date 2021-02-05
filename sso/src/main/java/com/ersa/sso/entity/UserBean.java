package com.ersa.sso.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author Vanessa Yang
 * @Data 2021/1/29 0029 11:35
 */
@Data
@Entity
@Table(schema = "test01", name = "sys_user")
public class UserBean implements Serializable {
    private static final long serialVersionUID = 5872438942257394882L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "open_id")
    private String openId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    /**
     * 性别： 1 女 || 2 男
     */
    @Column(name = "sex")
    private Integer sex;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private Integer status = 0;

    @Column(name = "create_user")
    private String createUser;


    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_user")
    private String updateUser;

    @Column(name = "update_time")
    private Date updateTime;

}
