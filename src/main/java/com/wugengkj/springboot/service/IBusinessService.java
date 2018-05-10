package com.wugengkj.springboot.service;

import com.baomidou.mybatisplus.service.IService;
import com.wugengkj.springboot.entity.Business;

import java.io.Serializable;

/**
 * @author leaf
 * <p>date: 2018-05-10 09:06</p>
 * <p>version: 1.0</p>
 */
public interface IBusinessService extends IService<Business> {
    /**
     * 查询指定id商家
     *
     * @param id 商家编号
     * @return 商家信息
     */
    Business queryOneById(Long id);

    /**
     * 查询appid
     *
     * @param id 商家编号
     * @return appid
     */
    String queryAppidById(Long id);
}
