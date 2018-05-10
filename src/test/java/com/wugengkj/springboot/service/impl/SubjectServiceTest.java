package com.wugengkj.springboot.service.impl;

import com.wugengkj.springboot.service.ISubjectService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zwl on 2018/5/10.
 * May god bless me
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SubjectServiceTest {
    @Autowired
    private ISubjectService iSubjectService;
    @Test
    public void queryList() throws Exception {
        iSubjectService.queryList(-1);
        iSubjectService.queryList(-2);
    }

}