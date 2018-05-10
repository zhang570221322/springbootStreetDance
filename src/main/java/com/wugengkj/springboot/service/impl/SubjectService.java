package com.wugengkj.springboot.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wugengkj.springboot.entity.Subject;
import com.wugengkj.springboot.mapper.SubjectMapper;
import com.wugengkj.springboot.service.ISubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwl on 2018/5/10.
 * May god bless me
 */
@Service
@Slf4j
public class SubjectService extends ServiceImpl<SubjectMapper, Subject> implements ISubjectService {

    @Override
    @Cacheable(value = "queryList", key = "#p0")
    public List<Subject> queryList(int key) {
        return super.selectList(new EntityWrapper<>());
    }

    /**
     *  根据用户的openid获取对应难度依次递增的9道题目
     *
     * @param openid
     * @return
     */
    @Override
    public List<Subject> getRandomList(String openid) {
        List<Subject> subjectList = this.queryList(-1);
        List<Subject> list = new ArrayList<>();
        return list;
    }
}
