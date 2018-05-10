package com.wugengkj.springboot.service.impl;

import com.wugengkj.springboot.entity.Subject;
import com.wugengkj.springboot.service.ISubjectService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SubjectServiceImplTest {

    @Autowired
    private ISubjectService subjectService;

    @Test
    public void queryList() {
        List<Subject> subjects = subjectService.queryList(-1);
        log.debug(String.valueOf(subjects.size()));
    }

    @Test
    public void getRandomList() {
    }

    @Test
    public void validSubjectResult() {
    }
}