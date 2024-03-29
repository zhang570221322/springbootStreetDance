package com.wugengkj.dance.service;

import com.baomidou.mybatisplus.service.IService;
import com.wugengkj.dance.entity.Ticket;

import java.util.List;

/**
 * @author leaf
 * <p>date: 2018-05-10 14:21</p>
 * <p>version: 1.0</p>
 */
public interface ITicketService extends IService<Ticket> {

    /**
     * 获取用户票信息
     *
     * @param openId 用户openId
     * @return 票信息
     */
    Ticket queryOneByOpenId(String openId);

    /**
     * 减少票
     *
     * @param ticketId 票id
     * @param businessId 商家id
     * @return 成功/失败
     */
    boolean reduceOneTicket(Long ticketId, Long businessId);

    /**
     * 获取票类型
     *
     * @return
     */
     List<Ticket> queryList();

    /**
     * 清理缓存
     */
    void removeCache();

}
