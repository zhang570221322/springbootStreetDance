package com.wugengkj.springboot.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wugengkj.springboot.common.enums.UserStatus;
import com.wugengkj.springboot.entity.User;
import com.wugengkj.springboot.mapper.UserMapper;
import com.wugengkj.springboot.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author leaf
 * <p>date: 2017-12-31 2:22</p>
 */
@Service
@CacheConfig(cacheNames = "users")
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public boolean addUser(User user) {
        // 用户提交个人信息
        boolean b = insert(user);
        forceUpdateCache(user.getOpenId(), b);
        return b;
    }

    @Cacheable(key = "#p0")
    @Override
    public User queryOneByOpenId(String openId) {
        return selectById(openId);
    }

    @Override
    public int queryUserStatus(String openId) {
        User user = queryOneByOpenId(openId);
        if (user == null) {
            return UserStatus.USER_NOT_FOUND.getCode();
        }
        return user.getStatus();
    }

    @Override
    public boolean updateUserStatus(User user) {
        boolean b = updateById(user);
        forceUpdateCache(user.getOpenId(), b);
        return b;
    }

    @Override
    public User queryByOrderCol(String colName, String colVal) {
        return selectOne(new EntityWrapper<User>().eq(colName, colName));
    }

    @CacheEvict(allEntries = true)
    @Override
    public void removeCache() {
        log.info("清理users缓存!");
    }

    @CachePut(key = "#p0")
    public User forceUpdateCache(String openId, boolean b) {
        if (openId != null && !openId.isEmpty() && b) {
            return selectById(openId);
        }
        return User.builder().build();
    }

}
