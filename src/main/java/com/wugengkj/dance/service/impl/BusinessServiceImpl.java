package com.wugengkj.dance.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wugengkj.dance.entity.Business;
import com.wugengkj.dance.mapper.BusinessMapper;
import com.wugengkj.dance.service.IBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author leaf
 * <p>date: 2018-05-10 09:08</p>
 * <p>version: 1.0</p>
 */
@Service
@CacheConfig(cacheNames = "business")
@Slf4j
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, Business> implements IBusinessService {

    @Cacheable(key   = "#p0")
    @Override
    public Business queryOneById(Long id) {
        log.info("添加business缓存!");
        return selectById(id);
    }

    @Override
    public String queryAppidById(Long id) {
        Business business = queryOneById(id);
        String appid = "";
        if (business != null) {
            appid = business.getAppid();
        }
        return appid;
    }

    @Override
    public synchronized boolean reduceOneTicket(Long businessId) {
        Business business = selectById(businessId);
        Integer surplusTicket = business.getSurplusTicket();
        if (surplusTicket > 0) {
            log.info("商家票数减少1，票总计数" + business.getTotalTicket() + "，当前剩余票数" + (surplusTicket - 1));
            boolean b = updateById(Business.builder().id(businessId).surplusTicket(surplusTicket - 1).build());
            if (b) {
                log.info("强制更新business缓存!");
                forceUpdateCache(businessId, b);
            }
            return b;
        } else {
            return false;
        }
    }

    @CacheEvict(allEntries = true)
    @Override
    public void removeCache() {
        log.info("清理business缓存信息!");
    }

    @CachePut(key = "#p0")
    public Business forceUpdateCache(Long id, boolean b) {
        if (id != null && b) {
            return selectById(id);
        }
        return queryOneById(id);
    }

}
