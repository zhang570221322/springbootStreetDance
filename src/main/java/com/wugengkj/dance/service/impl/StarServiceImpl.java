package com.wugengkj.dance.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wugengkj.dance.entity.Star;
import com.wugengkj.dance.mapper.StarMapper;
import com.wugengkj.dance.service.IStarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author leaf
 * <p>date: 2018-05-12 16:08</p>
 * <p>version: 1.0</p>
 */
@Service
@CacheConfig(cacheNames = "starts")
@Slf4j
public class StarServiceImpl extends ServiceImpl<StarMapper, Star> implements IStarService {

    @Cacheable(key = "#p0")
    @Override
    public List<Star> getAll(int key) {
        return selectList(new EntityWrapper<>());
    }

    @CacheEvict(allEntries = true)
    @Override
    public void removeCache() {
        log.info("清理starts缓存信息!");
    }
}
