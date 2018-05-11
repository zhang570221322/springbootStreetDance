package com.wugengkj.dance.service;

import com.baomidou.mybatisplus.service.IService;
import com.wugengkj.dance.entity.Record;

import java.util.List;
import java.util.Map;

/**
 * @author leaf
 * <p>date: 2018-05-10 14:29</p>
 * <p>version: 1.0</p>
 */
public interface IRecordService extends IService<Record> {

    /**
     * 批量获取指定openId答题记录
     *
     * @param openId openId
     * @return 答题记录表
     */
    List<Record> queryListByOpenId(String openId);

    /**
     * 批量添加答题记录
     *
     * @param openId openId
     * @param subjects 题目
     * @return
     */
    boolean addBatchByOpenIdAndSubjects(String openId, List<Long> subjects);

    /**
     * 批量更新答题结果
     *
     * @param openId openId
     * @param results 用户结果集
     * @return
     */
    boolean updateBatchIsTrue(String openId, Map<String, Integer> results);

    /**
     * 清理缓存
     */
    void removeCache();
}
