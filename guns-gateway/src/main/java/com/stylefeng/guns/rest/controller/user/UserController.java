package com.stylefeng.guns.rest.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dodo.service.bean.UserParams;
import com.dodo.service.UserService;
import com.dodo.vo.BaseVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Reference(interfaceClass = UserService.class,check = false)
    private UserService userService;


    @RequestMapping("/register")
    public BaseVo register(UserParams userRegister){
        BaseVo baseVo = userService.register(userRegister);
        return baseVo;
    }

    @RequestMapping("/check")
    public BaseVo check(String username){
        BaseVo baseVo = new BaseVo();
        Integer integer = userService.check(username);
        if(integer == 1){
            baseVo.setStatus(1);
            baseVo.setMsg("用户已存在");
            return baseVo;
        }else if(integer == 0) {
            baseVo.setStatus(0);
            baseVo.setMsg("用户不存在");
            return baseVo;
        }
        baseVo.setStatus(999);
        baseVo.setMsg("系统出现异常，请联系管理员");
        return baseVo;
    }
}
