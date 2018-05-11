package com.wugengkj.dance.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wugengkj.dance.entity.Ticket;
import com.wugengkj.dance.mapper.TicketMapper;
import com.wugengkj.dance.service.IBusinessService;
import com.wugengkj.dance.service.ITicketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author leaf
 * <p>date: 2018-05-10 14:22</p>
 * <p>version: 1.0</p>
 */
@Service
@CacheConfig(cacheNames = "tickets")
@Slf4j
public class TicketServiceImpl extends ServiceImpl<TicketMapper, Ticket> implements ITicketService {

    @Autowired
    private IBusinessService businessService;

    @Cacheable(key = "#p0")
    @Override
    public Ticket queryOneByOpenId(String openId) {
        Ticket ticket = baseMapper.selectOneByOpenId(openId);
        if (ticket.getId() != -1) {
            return ticket;
        }
        return null;
    }

    @Override
    public synchronized boolean reduceOneTicket(Long ticketId, Long businessId) {
        log.info("开始减少票id为" + ticketId + "的数量，减少数量为1!");
        // 更新单张票数
        Ticket ticket = selectById(ticketId);
        Integer currentNum = ticket.getCurrentNum();
        if (currentNum > 0) {
            ticket.setCurrentNum(currentNum - 1);
            // 更新商家总票数
            return updateById(ticket) && businessService.reduceOneTicket(businessId);
        } else {
            return false;
        }
    }

    @CacheEvict(allEntries = true)
    @Override
    public void removeCache() {
        log.info("清理tickets缓存信息!");
    }
}
