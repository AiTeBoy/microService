package com.stylefeng.guns.rest.controller.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dodo.user.bean.UserParams;
import com.dodo.user.UserService;
import com.dodo.vo.BaseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Reference(interfaceClass = UserService.class,check = false)
    private UserService userService;

    @Autowired
    RedisTemplate redisTemplate;


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

    @RequestMapping("/logout")
    public BaseVo logout(HttpServletRequest httpServletRequest){
        BaseVo baseVo = new BaseVo();
        String header = httpServletRequest.getHeader("Authorization");
        if(header == null){//用户没有登录
            baseVo.setStatus(0);
            baseVo.setMsg("退出失败，用户尚未登陆");
            return baseVo;
        }
        String token = header.substring(7);
        Boolean result = userService.logout(token);
        if(result == true) {
            baseVo.setStatus(0);
            baseVo.setMsg("成功退出");
            return baseVo;
        }
        baseVo.setStatus(999);
        baseVo.setMsg("系统出现异常，请联系管理员");
        return baseVo;
    }

}
