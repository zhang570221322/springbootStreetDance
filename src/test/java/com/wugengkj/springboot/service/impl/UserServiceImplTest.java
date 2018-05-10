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
        //数据库没数据了，我注释了
        //Java1.9不能和lombok配合，不知道为啥，我换成1.8 就OK了，可能是pom里面lombok的版本太低了把
        //吐槽一下，是启动是真的慢。
//        log.info(user.toString());
//        log.info(user1.toString());
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