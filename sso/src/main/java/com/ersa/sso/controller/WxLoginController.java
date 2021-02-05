package com.ersa.sso.controller;

import com.ersa.sso.entity.UserBean;
import com.ersa.sso.service.UserService;
import com.ersa.sso.utils.ConstantUtil;
import com.ersa.sso.utils.HttpClientUtil;
import com.ersa.sso.utils.JwtUtil;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sun.net.www.http.HttpClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @Description
 * @Author Vanessa Yang
 * @Data 2021/2/5 0005 9:32
 */
@Controller
@RequestMapping("/api/ucenter/wx")
@CrossOrigin
public class WxLoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String callback(String code, String state) {
        String baseAccessTokenUrl = "http://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        String accessTokenUrl = String.format(
                baseAccessTokenUrl,
                ConstantUtil.WX_OPEN_APP_ID,
                ConstantUtil.WX_OPEN_APP_SECRET,
                code
        );

        String result = null;
        try {
            //创建Http Get连接
            result = HttpClientUtil.get(accessTokenUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("result = " + result);

        Gson gson = new Gson();
        HashMap hashMap = gson.fromJson(result, HashMap.class);
        String accessToken = (String) hashMap.get("access_token");
        String openId = (String) hashMap.get("openid");

        List<UserBean> users = userService.getByOpenId(openId);
        UserBean user = null;
        if (CollectionUtils.isEmpty(users)) {
            String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId;

            String userInfo = null;
            try {
                //创建Https Get连接
                userInfo = HttpClientUtil.get(baseUserInfoUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("userInfo = " + userInfo);

            HashMap map = gson.fromJson(userInfo, HashMap.class);
            String nickname = (String) map.get("nickname");
            String headImgUrl = (String) map.get("headimgurl");
            UserBean userBean = new UserBean();
            userBean.setNickname(nickname);
            userBean.setAvatar(headImgUrl);
            userBean.setOpenId(openId);
            user = userService.addUser(userBean);
        }else {
            user = users.get(0);
        }
        String jwtToken = JwtUtil.getJwtToken(String.valueOf(user.getId()), user.getNickname());
        System.out.println("jwtToken = " + jwtToken);
        return "redirect:http://localhost:8150/";
    }

    @GetMapping("/login")
    public String getWxCode() {
        //固定地址及参数，获得微信登录的二维码
        String baseUrl = "http://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";
        //获得业务服务器重定向地址
        String redirectUrl = ConstantUtil.WX_OPEN_REDIRECT_URL;
        try {
            //url编码
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = String.format(
                baseUrl,
                ConstantUtil.WX_OPEN_APP_ID,
                redirectUrl,
                "panzitech"
        );
        return "redirect:" + url;

    }
}
