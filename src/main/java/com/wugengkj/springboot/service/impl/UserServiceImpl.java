package com.wugengkj.springboot.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wugengkj.springboot.common.enums.UserStatus;
import com.wugengkj.springboot.entity.User;
import com.wugengkj.springboot.mapper.UserMapper;
import com.wugengkj.springboot.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @author leaf
 * <p>date: 2017-12-31 2:22</p>
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {



    @Override
    public boolean addOne(User user) {
        return insert(user);
    }

    @Cacheable(value = "user", key = "#p0")
    @Override
    public User queryOneByOpenId(Serializable openId) {
        return selectById(openId);
    }

    @Override
    public int queryUserStatus(Serializable openId) {
        User user = queryOneByOpenId(openId);
        if (user == null) {
            return UserStatus.USER_NOT_FOUND.getCode();
        }
        return user.getStatus();
    }

    @Override
    public User queryOneByName(String name) {
        return selectOne(new EntityWrapper<User>().eq("name", name));
    }

    @Override
    public Page<User> queryList(Page<User> page) {
        return page.setRecords(selectList(new EntityWrapper<>()));
    }

    @Override
    public Page<User> queryListByType(Page<User> page, Integer type) {
        return page.setRecords(selectList(new EntityWrapper<User>().eq("type", type)));
    }
}
