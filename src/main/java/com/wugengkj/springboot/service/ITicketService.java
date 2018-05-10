package com.wugengkj.springboot.service;

import com.baomidou.mybatisplus.service.IService;
import com.wugengkj.springboot.entity.Ticket;

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

}
