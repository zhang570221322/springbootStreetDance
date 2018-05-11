package com.wugengkj.springboot.service;

import com.baomidou.mybatisplus.service.IService;
import com.wugengkj.springboot.entity.User;

import java.io.Serializable;

/**
 * @author leaf
 * <p>date: 2017-12-31 1:47</p>
 */
public interface IUserService extends IService<User> {

    /**
     * 添加用户信息
     *
     * @param user 用户信息
     * @return 是否添加成功
     */
   boolean addUser(User user);

    /**
     * 查询指定openId信息
     *
     * @param openId openId
     * @return 用户信息
     */
    User queryOneByOpenId(String openId);

    /**
     * 查询用户状态
     *
     * @param openId openId
     * @return 状态
     */
    int queryUserStatus(String openId);

    /**
     * 更新用户状态
     *
     * @param user 用户信息
     * @return
     */
    boolean updateUserStatus(User user);

    /**
     * 查询指定列是否存在
     *
     * @param colName 列名
     * @param colVal 列值
     * @return
     */
    User queryByOrderCol(String colName, String colVal);

    /**
     * 清理缓存
     */
    void removeCache();
}
