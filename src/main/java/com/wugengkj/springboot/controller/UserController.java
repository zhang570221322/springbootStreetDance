package com.wugengkj.springboot.controller;

import com.wugengkj.springboot.common.vo.ResponseInfoVO;
import com.wugengkj.springboot.service.IUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户请求
 *
 * @author leaf
 * <p>date: 2018-05-07 17:11</p>
 * <p>version: 1.0</p>
 */
@Api(value = "用户请求相关", description = "/user")
@RequestMapping("/user")
@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/info")
    public ResponseInfoVO info(String openId) {
        return ResponseInfoVO.success(userService.queryOneByOpenId(openId));
    }
}
