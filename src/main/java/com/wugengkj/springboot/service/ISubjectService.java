package com.wugengkj.springboot.service;

import com.baomidou.mybatisplus.service.IService;
import com.wugengkj.springboot.entity.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwl on 2018/5/10.
 * May god bless me
 */
public interface ISubjectService extends IService<Subject> {
     /**
      * 得到所有题库题
      * @param key 传-1就会缓存
      * @return
      */
     List<Subject> queryList(int key);

     /**
      * 获取难度依次递增的9道题目
      * @return
      */
     List<Subject>  getRandomList(String openid);
}
