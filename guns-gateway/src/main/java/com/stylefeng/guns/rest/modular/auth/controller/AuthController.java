package com.stylefeng.guns.rest.modular.auth.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dodo.service.UserService;
import com.dodo.service.bean.UserParams;
import com.dodo.vo.BaseVo;
import com.stylefeng.guns.core.exception.GunsException;
import com.stylefeng.guns.rest.common.exception.BizExceptionEnum;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthRequest;
import com.stylefeng.guns.rest.modular.auth.controller.dto.AuthResponse;
import com.stylefeng.guns.rest.modular.auth.util.JwtTokenUtil;
import com.stylefeng.guns.rest.modular.auth.validator.IReqValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 请求验证的
 *
 * @author fengshuonan
 * @Date 2017/8/24 14:22
 */
@RestController
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Reference(interfaceClass = UserService.class,check = false)
    private UserService userService;

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping(value = "${jwt.auth-path}")
    public BaseVo createAuthenticationToken(AuthRequest authRequest) {
        BaseVo baseVo = new BaseVo();
        //boolean validate = reqValidator.validate(authRequest);

        //first：用户名和密码和数据库进行比对
        UserParams userInfo = userService.login(authRequest.getUserName(), authRequest.getPassword());
        if (userInfo != null) {//若认证通过，则生成token令牌
            final String randomKey = jwtTokenUtil.getRandomKey();
            final String token = jwtTokenUtil.generateToken(authRequest.getUserName(), randomKey);
            //生成的token令牌保存在Redis
            redisTemplate.opsForValue().set(token,userInfo,5*60, TimeUnit.SECONDS);
            //返回成功的json
            Map<String,Object> map = new HashMap<>();
            map.put("randomKey",randomKey);
            map.put("token",token);
            baseVo.setData(map);
            baseVo.setStatus(0);
            return baseVo;
        } else {//否则报错
            baseVo.setStatus(1);
            baseVo.setMsg("用户名或密码错误");
            return baseVo;
        }
    }
}
