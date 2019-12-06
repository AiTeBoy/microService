package com.dodo.user;

import com.dodo.user.bean.UserParams;
import com.dodo.vo.BaseVo;

public interface UserService {

    //用户注册
    BaseVo register(UserParams userRegister);

    //用户登录
   UserParams login(String userName, String password);

    //检查用户名是否注册过
    Integer check(String username);

    //用户退出
    Boolean logout(String token);
}
