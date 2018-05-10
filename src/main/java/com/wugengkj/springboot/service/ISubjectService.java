package com.wugengkj.springboot.service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.IService;
import com.wugengkj.springboot.entity.Subject;

import java.util.List;

/**
 * Created by zwl on 2018/5/10.
 * May god bless me
 */
public interface ISubjectService extends IService<Subject> {
     List<Subject> queryList();
}
