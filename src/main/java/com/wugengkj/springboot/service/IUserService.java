package com.wugengkj.springboot.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.wugengkj.springboot.entity.User;

import java.io.Serializable;

/**
 * @author leaf
 * <p>date: 2017-12-31 1:47</p>
 */
public interface IUserService extends IService<User> {

    /**
     * 添加单个用户
     *
     * @param user 用户信息
     * @return true/false(添加成功/添加失败)
     */
    boolean addOne(User user);

    /**
     * 查询指定openId信息
     *
     * @param openId openId
     * @return 用户信息
     */
    User queryOneByOpenId(Serializable openId);

    /**
     * 查询用户状态
     *
     * @param openId openId
     * @return 状态
     */
    int queryUserStatus(Serializable openId);

    /**
     * 查询指定用户名信息
     *
     * @param name 用户名
     * @return 用户信息
     */
    User queryOneByName(String name);

    /**
     * 查询用户列表
     *
     * @param page 分页信息
     * @return 分页用户信息
     */
    Page<User> queryList(Page<User> page);

    /**
     * 查询指定类型用户列表
     *
     * @param page 分页信息
     * @param type 用户类型
     * @return 分页用户信息
     */
    Page<User> queryListByType(Page<User> page, Integer type);
}
