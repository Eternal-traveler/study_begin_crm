package com.haoyang.commons.utils;

import com.haoyang.commons.constants.Constants;
import com.haoyang.settings.pojo.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hao yang
 * @date 2022-08-08-16:28
 */
public class CookieUtils {

    /**
     * 销毁 用户登录 过程产生的cookie
     * @param response 响应信息
     */
    public static void destroyLoginCookie(HttpServletResponse response){
        //清空cookie（但这样会不会不严谨，后面再自己学一下）
        //如果没有记住，把没有过期的Cookie删除
        Cookie cookieAct = new Cookie("loginAct", null);
        cookieAct.setMaxAge(0);
        response.addCookie(cookieAct);
        Cookie cookiePwd = new Cookie("loginPwd", null);
        cookiePwd.setMaxAge(0);
        response.addCookie(cookiePwd);
        //无法更改用户电脑上的文件，可以覆盖
    }

    /**
     * 创建 用户登录 过程需要的cookie
     * @param user 登录的用户
     * @param response 响应信息
     */
    public static void createLoginCookie(User user , HttpServletResponse response){
        Cookie cookieAct = new Cookie("loginAct", null);
        cookieAct.setMaxAge(10*24*60*60);
        response.addCookie(cookieAct);
        Cookie cookiePwd = new Cookie("loginPwd", null);
        cookiePwd.setMaxAge(10*24*60*60);
        response.addCookie(cookiePwd);
        // 新建一个存放用户id的cookie，代表该用户的唯一标识，用于登录时用户判断
        Cookie cookieUserId = new Cookie(Constants.COOKIE_NAME_ID, user.getId());
        cookieUserId.setMaxAge(Constants.COOKIE_MAX_ALIVE_TIME);
        response.addCookie(cookieUserId);
    }

    /**
     * 通过cookie的value值从cookie数组中查找cookie
     * @param cookies 查找的cookie数组
     * @param value 被查找的cookie的value值
     * @return 找到返回true，没找到返回false
     */
    public static boolean findCookieByValue(Cookie[] cookies, String value) {
        for (Cookie cookie : cookies) {
            if (cookie.getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 通过cookie的name值从cookie数组中查找cookie
     * @param cookies 查找的cookie数组
     * @param name 被查找的cookie的name值
     * @return 找到返回true，没找到返回false
     */
    public static boolean findCookieByName(Cookie[] cookies, String name) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
