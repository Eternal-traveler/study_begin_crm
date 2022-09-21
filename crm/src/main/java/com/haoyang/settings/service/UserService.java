package com.haoyang.settings.service;

import com.haoyang.settings.pojo.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author haoyang
 * @reate 2022-07-29-21:22
 */

@Service
public interface UserService {

    /**
     * 进行查询用户信息
     * @param map
     * @return
     */
    public User queryUserByLoginActAndPwd(Map<String,Object> map);

    /**
     * 返回用户信息
     * @return
     */
    public List<User> queryAllUsers();
}
