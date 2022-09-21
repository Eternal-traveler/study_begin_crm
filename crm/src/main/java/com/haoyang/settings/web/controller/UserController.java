package com.haoyang.settings.web.controller;

import com.haoyang.commons.constants.Constants;
import com.haoyang.commons.pojo.ReturnObject;
import com.haoyang.commons.utils.CookieUtils;
import com.haoyang.commons.utils.DateUtils;
import com.haoyang.settings.pojo.User;
import com.haoyang.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author haoyang
 * @reate 2022-07-27-17:35
 */
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin() {
        //请求转发
        return "/settings/qx/user/login";
    }

    @RequestMapping("/settings/qx/user/toRegister.do")
    public String toRegister() {
        return "/settings/qx/user/register";
    }

    /**
     *看方法响应信息返回到哪个页面
     *那个页面的资源路径一样
     *资源名称和方法名一样
     * @return json字符串
     */
    @RequestMapping("/settings/qx/user/login.do")
    public @ResponseBody Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request,HttpServletResponse response, HttpSession session){
        //封装成map
        Map<String, Object> map = new HashMap<>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);
        //调用service层方法，查询用户信息
        User user = userService.queryUserByLoginActAndPwd(map);
        ReturnObject returnObject = new ReturnObject();
        //根据查询结果，生成响应信息
        if(user == null){
            //登陆失败,用户名或密码错误
            returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("用户名或密码错误");
        }else{//判断账号是否合法
            if(DateUtils.formatDateTime(new Date()).compareTo(user.getExpireTime()) > 0){
                //登陆失败，账号已过期
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("账号已过期");
            }else if("0".equals(user.getLockState())){
                //登陆失败，账号被锁定
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("账号被锁定");
                /*
                   request.getRemoteAddr()  获得客户端的ip地址
                   request.getRemoteHost()  获得客户端的主机名
                 */
            }else if(!user.getAllowIps().contains(request.getRemoteAddr())){
                //登陆失败，ip受限
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("ip受限");
            }else{
                //登陆成功
                returnObject.setCode(Constants.RETURN_OBJECT_CODE_SUCCESS);
                //把user数据保存到session中,这个key没写死，设置成常量
                session.setAttribute(Constants.SESSION_USER, user);
                // 如果需要记住密码，则创建对应的cookie对象（不安全，如何解决？）
                if("true".equals(isRemPwd)){
                    // 检验当前是否已经存在对应的账号和密码cookie，如果存在则不再创建
                    Cookie[] cookies = request.getCookies();
                    // 判断是否是一个新用户，isHaveCookie用来标记是否存在当前用户的cookie
                    boolean isHaveCookie = CookieUtils.findCookieByValue(cookies, user.getId());
                    // 如果不存在cookie，那么就创建当前新用户的cookie
                    if (!isHaveCookie) {
                        CookieUtils.createLoginCookie(user, response);
                    }
                }else{
                    CookieUtils.destroyLoginCookie(response);
                }
            }
        }

        return returnObject;
    }

    /**
     * 安全退出功能
     * @param response 响应
     * @param session 操作session
     * @return 重定向到首页
     */
    @RequestMapping("/settings/qx/user/logout.do")
    public String logout(HttpSession session,HttpServletResponse response){
        CookieUtils.destroyLoginCookie(response);
        //销毁session
        session.invalidate();
        //防止发生两次请求，重定向
        //借助springmvc来重定向, response.sendRedirect("/crm/");
        return "redirect:/";
    }


}
