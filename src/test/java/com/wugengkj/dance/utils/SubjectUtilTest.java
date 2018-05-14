package com.wugengkj.dance.utils;

import com.wugengkj.dance.entity.Subject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zwl on 2018/5/12.
 * May god bless me
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class SubjectUtilTest {
    @Autowired
    private SubjectUtil subjectUtil;
    @Test
    public void ticketId() throws Exception {
        System.out.println(subjectUtil.ticketId(3)+"**********");
    }

    @Test
    public void randomSubject() throws Exception {
        List<Subject> randomSubject = subjectUtil.randomSubject();
        randomSubject.forEach(subject -> System.out.println(subject));
    }

}