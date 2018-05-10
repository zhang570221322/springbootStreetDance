package com.wugengkj.springboot.service.impl;

import com.wugengkj.springboot.entity.Subject;
import com.wugengkj.springboot.entity.Ticket;
import com.wugengkj.springboot.service.ISubjectService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        subjectService.getRandomList("1");
    }

    @Test
    public void validSubjectResult() {
        Map s = new HashMap<Long, String>();
        s.put(1L, "A");
        Ticket ticket = subjectService.postUserSubjectResult("1", s);

        System.out.println(ticket);
    }
}