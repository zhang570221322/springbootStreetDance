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


import java.util.List;

/**
 * Created by zwl on 2018/5/10.
 * May god bless me
 */
@Service
@Slf4j
public class SubjectService  extends ServiceImpl<SubjectMapper, Subject> implements ISubjectService{
    @Override
    @Cacheable(value = "queryList",key="getAll")
    public List<Subject> queryList() {
        return super.selectList(new EntityWrapper<>());
    }
}
