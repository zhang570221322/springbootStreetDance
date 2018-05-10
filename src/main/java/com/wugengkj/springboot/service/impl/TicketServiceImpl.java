package com.wugengkj.springboot.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wugengkj.springboot.entity.Ticket;
import com.wugengkj.springboot.mapper.TicketMapper;
import com.wugengkj.springboot.service.ITicketService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author leaf
 * <p>date: 2018-05-10 14:22</p>
 * <p>version: 1.0</p>
 */
@Service
public class TicketServiceImpl extends ServiceImpl<TicketMapper, Ticket> implements ITicketService {

    @Cacheable(value = "tickets", key = "#p0")
    @Override
    public Ticket queryOneByOpenId(String openId) {
        return baseMapper.selectOneByOpenId(openId);
    }
}
