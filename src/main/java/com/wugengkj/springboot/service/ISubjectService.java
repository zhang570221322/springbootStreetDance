package com.wugengkj.springboot.service;

import com.baomidou.mybatisplus.service.IService;
import com.wugengkj.springboot.entity.Subject;
import com.wugengkj.springboot.entity.Ticket;

import java.util.List;
import java.util.Map;

/**
 * @author zwl
 * @date 2018/5/10
 * May god bless me
 */
public interface ISubjectService extends IService<Subject> {
    /**
     * 题目列表
     *
     * @param key 默认-1
     * @return
     */
    List<Subject> queryList(int key);

    /**
     * 获取难度依次递增的9道题目
     *
     * @param openId
     * @return
     */
    List<Subject> getRandomList(String openId);

    /**
     * 提交用户答案结果
     *
     * @param openId 用户编号
     * @param results 最终结果
     * @return
     */
    Ticket postUserSubjectResult(String openId, Map<Long, String> results);

}
