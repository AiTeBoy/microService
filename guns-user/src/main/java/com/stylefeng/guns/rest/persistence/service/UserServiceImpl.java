package com.stylefeng.guns.rest.persistence.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.dodo.user.bean.UserParams;
import com.dodo.user.UserService;
import com.dodo.vo.BaseVo;
import com.stylefeng.guns.core.util.MD5Util;
import com.stylefeng.guns.rest.persistence.dao.MtimeUserTMapper;
import com.stylefeng.guns.rest.persistence.model.MtimeUserT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService{

    @Autowired
    MtimeUserTMapper mtimeUserTMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public BaseVo register(UserParams userRegister) {
        BaseVo baseVo = new BaseVo();
        MtimeUserT mtimeUserT = new MtimeUserT();
        copyValueToDb(userRegister,mtimeUserT);
        //先判断用户名是否有被注册，注册返回：用户已存在
        Integer integer = check(userRegister.getUsername());
        if(integer == 1){//查询返回值为1，则已经注册了
            baseVo.setStatus(1);
            baseVo.setMsg("用户已存在");
            return baseVo;
        }else if(integer == 0){//查询返回值为0，没有被注册过
            //注册时候需要密码加密
            mtimeUserT.setUserPwd(MD5Util.encrypt(mtimeUserT.getUserPwd()));
            Integer insert = mtimeUserTMapper.insert(mtimeUserT);
            if(insert == 1) {//为1，注册成功
                baseVo.setStatus(0);
                baseVo.setMsg("注册成功");
                return baseVo;
            }
        }
        baseVo.setStatus(999);
        baseVo.setMsg("系统出现异常，请联系管理员");
        return baseVo;
    }

    @Override
    public UserParams login(String userName, String password) {
        UserParams userInfo = null;
        EntityWrapper<MtimeUserT> wrapper = new EntityWrapper<>();
        wrapper.eq("user_name",userName).eq("user_pwd",MD5Util.encrypt(password));
        List<MtimeUserT> mtimeUserTS = mtimeUserTMapper.selectList(wrapper);
        if(mtimeUserTS != null) {
            MtimeUserT mtimeUserT = mtimeUserTS.get(0);
            userInfo = new UserParams();
            copyValueToFront(userInfo,mtimeUserT);
        }
        return userInfo;
    }

    @Override
    public Integer check(String username) {
        EntityWrapper<MtimeUserT> wrapper = new EntityWrapper<>();
        wrapper.eq("user_name",username);
        Integer integer = mtimeUserTMapper.selectCount(wrapper);
        return integer;
    }

    @Override
    public Boolean logout(String token) {
        if(!redisTemplate.hasKey(token)){
            return true;
        }
        Boolean delete = redisTemplate.delete(token);
        return delete;
    }

    //数据库的字段和前端传过来的参数字段名不一致，需要转换
    public void copyValueToDb(UserParams userParams,MtimeUserT mtimeUserT){
        mtimeUserT.setUserName(userParams.getUsername());
        mtimeUserT.setUserPwd(userParams.getPassword());
        mtimeUserT.setUserPhone(userParams.getMobile());
        mtimeUserT.setEmail(userParams.getEmail());
        mtimeUserT.setAddress(userParams.getAddress());
    }

    public void copyValueToFront(UserParams userParams,MtimeUserT mtimeUserT){
        userParams.setId(mtimeUserT.getUuid());
        userParams.setUsername(mtimeUserT.getUserName());
        userParams.setPassword(mtimeUserT.getUserPwd());
        userParams.setEmail(mtimeUserT.getEmail());
        userParams.setAddress(mtimeUserT.getAddress());
    }
}
