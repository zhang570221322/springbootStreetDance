package com.wugengkj.dance.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wugengkj.dance.common.enums.UserStatus;
import com.wugengkj.dance.entity.User;
import com.wugengkj.dance.mapper.UserMapper;
import com.wugengkj.dance.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private IUserService userService;

    @Override
    public boolean addUser(User user) {
        // 用户提交个人信息
        boolean b = insert(user);
        userService.removeCache();
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
        userService.removeCache();
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

}
