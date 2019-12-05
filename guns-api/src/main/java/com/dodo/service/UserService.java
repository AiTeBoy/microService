package com.dodo.service;

import com.dodo.service.bean.UserParams;
import com.dodo.vo.BaseVo;

public interface UserService {

    //用户注册
    BaseVo register(UserParams userRegister);

    //用户登录
   UserParams login(String userName, String password);

    //检查用户名是否注册过
    Integer check(String username);
}
