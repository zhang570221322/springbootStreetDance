package com.wugengkj.dance.service;

import com.baomidou.mybatisplus.service.IService;
import com.wugengkj.dance.entity.Business;

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

    /**
     * 减少一张票
     *
     * @param businessId 商家id
     * @return 成功/失败
     */
    boolean reduceOneTicket(Long businessId);

    /**
     * 清理缓存
     */
    void removeCache();
}
