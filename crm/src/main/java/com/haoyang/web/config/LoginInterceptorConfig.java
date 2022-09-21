package com.haoyang.web.config;

import com.haoyang.commons.constants.Constants;
import com.haoyang.settings.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author hao yang
 * @date 2022-08-01-18:43
 */
public class LoginInterceptorConfig implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        //判断有没有session
        if((User) session.getAttribute(Constants.SESSION_USER) == null){
            /**
             * 请求转发可以写资源的名字
             * 重定向必须加项目名称
             * 重定向时，url必须加项目名称
             */
            response.sendRedirect(request.getContextPath());
            return false;
        }
//        request.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(request, response);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
