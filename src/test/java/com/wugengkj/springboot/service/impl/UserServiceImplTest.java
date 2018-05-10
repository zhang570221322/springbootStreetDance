package com.wugengkj.springboot.service.impl;

import com.wugengkj.springboot.entity.User;
import com.wugengkj.springboot.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserServiceImplTest {

    @Autowired
    private IUserService userService;

    @Test
    public void queryOneByOpenId() {
        // 测试缓存是否生效
        User user = userService.queryOneByOpenId("1");
        User user1 = userService.queryOneByOpenId("1");
        log.info(user.toString());
        log.info(user1.toString());
    }

    @Test
    public void queryOneByName() {
    }

    @Test
    public void queryList() {
    }

    @Test
    public void queryListByType() {
    }
}