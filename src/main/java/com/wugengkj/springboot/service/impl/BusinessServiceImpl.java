package com.wugengkj.springboot.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wugengkj.springboot.entity.Business;
import com.wugengkj.springboot.mapper.BusinessMapper;
import com.wugengkj.springboot.service.IBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author leaf
 * <p>date: 2018-05-10 09:08</p>
 * <p>version: 1.0</p>
 */
@Service
@Slf4j
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, Business> implements IBusinessService {

    @Cacheable(value = "queryOneById", key   = "#p0")
    @Override
    public Business queryOneById(Long id) {
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
}
