package com.wugengkj.dance.service;

import com.wugengkj.dance.entity.Star;

import java.util.List;

/**
 * @author leaf
 * <p>date: 2018-05-12 16:08</p>
 * <p>version: 1.0</p>
 */
public interface IStarService {

    /**
     * 获取全部明星信息
     *
     * @param key
     * @return
     */
    List<Star> getAll(int key);

    /**
     * 清理缓存
     */
    void removeCache();
}
