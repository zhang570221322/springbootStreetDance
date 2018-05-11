package com.wugengkj.dance.service.impl;

import com.wugengkj.dance.entity.Subject;
import com.wugengkj.dance.entity.Ticket;
import com.wugengkj.dance.service.ISubjectService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Ignore
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

    @Transactional
    @Test
    public void getRandomList() {
        subjectService.getRandomList("1");
    }

    @Transactional
    @Test
    public void validSubjectResult() {
        Map s = new HashMap<Long, String>();
        s.put(1L, "A");
        Ticket ticket = subjectService.postUserSubjectResult("1", s);

        System.out.println(ticket);
    }

    @Transactional
    @Test
    public void addBatchSubject() {
        List<Subject> list = new ArrayList<>(100);
        for (int i = 1; i <= 100; i++) {
            list.add(Subject.builder()
                    .id((long) i)
                    .content(String.valueOf(i))
                    .title(String.valueOf(i))
                    .type(1)
                    .answer("A")
                    .build());
        }
        subjectService.addBatchSubject(list);
    }
}