package com.wugengkj.springboot.utils;

import com.wugengkj.springboot.entity.Subject;
import com.wugengkj.springboot.service.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwl on 2018/5/10.
 * May god bless me
 * 提供关于题目的算法
 */


@Component
public class SubjectUtil {
    @Autowired
    private ISubjectService iSubjectService;

    /**
     * 获取难度依次递增的题目
     * @return
     */
    public List<Subject>  getRandomList(){
        List<Subject> subjectList = iSubjectService.queryList(-1);
        List<Subject> list=new ArrayList<>();
        return list;
    }
}
