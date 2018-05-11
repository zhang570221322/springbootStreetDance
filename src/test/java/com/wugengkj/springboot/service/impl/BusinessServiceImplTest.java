package com.wugengkj.springboot.service.impl;

import com.wugengkj.springboot.entity.Business;
import com.wugengkj.springboot.service.IBusinessService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BusinessServiceImplTest {

    @Autowired
    private IBusinessService businessService;

    @Test
    public void queryOneById() {
        Business business = businessService.queryOneById(1L);
        log.info(business.toString());
    }

    @Test
    public void queryAppidById() {
        String s = businessService.queryAppidById(1L);
        log.info(s);
    }

    @Transactional
    @Test
    public void reduceOneTicket() {
        boolean b = businessService.reduceOneTicket(1L);
        log.info(String.valueOf(b));
    }
}