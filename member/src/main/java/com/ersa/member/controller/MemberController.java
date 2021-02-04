package com.ersa.member.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * @Description
 * @Author Vanessa Yang
 * @Data 2021/1/29 0029 17:06
 */
@RequestMapping("/member")
@Controller
public class MemberController {

    @GetMapping("/list")
    public String list(){
        return "member/list";
    }

    @GetMapping("/info")
    @ResponseBody
    public Principal info(Principal principal){
        return principal;
    }

    @GetMapping("/me")
    @ResponseBody
    public Authentication me(Authentication authentication){
        return authentication;
    }

    @PreAuthorize("hasAuthority('add')")
    @ResponseBody
    @GetMapping("/add")
    public String add(){
        return "add";
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @ResponseBody
    @GetMapping("/detail")
    public String detail() {
        return "detail";
    }
}
