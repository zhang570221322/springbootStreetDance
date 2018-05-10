package com.wugengkj.springboot.controller;

import com.wugengkj.springboot.common.enums.UserStatus;
import com.wugengkj.springboot.common.vo.ResponseInfoVO;
import com.wugengkj.springboot.entity.User;
import com.wugengkj.springboot.service.ITicketService;
import com.wugengkj.springboot.service.IUserService;
import com.wugengkj.springboot.utils.AccessTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    private ITicketService ticketService;

    @Autowired
    private IUserService userService;

    @ApiOperation("获取用户状态")
    @PostMapping(value = "status")
    public ResponseInfoVO status(@RequestParam("code") String code) {
        return ResponseInfoVO.success(userService.queryUserStatus(AccessTokenUtil.getOpenId(code)));
    }

    @ApiOperation("提交用户信息")
    @PostMapping("post")
    public ResponseInfoVO post(@RequestParam("code") String code,
                               @RequestParam("name") String name,
                               @RequestParam("age") Integer age,
                               @RequestParam("sex") String sex,
                               @RequestParam("phone") String phone,
                               @RequestParam("qq") String qq) {
        String openId = AccessTokenUtil.getOpenId(code);
        User build = User.builder()
                .openId(openId)
                .sex(sex)
                .age(age)
                .phone(phone)
                .qq(qq)
                .name(name)
                .status(UserStatus.USER_NO_ANSWER.getCode())
                .build();
        boolean b = userService.addUser(build, code);
        return b ? ResponseInfoVO.success(true) : ResponseInfoVO.fail(false);
    }

    @ApiOperation("获取用户得票信息")
    @PostMapping(value = "ticket")
    public ResponseInfoVO ticket(@RequestParam("code") String code) {
        String openId = AccessTokenUtil.getOpenId(code);
        return ResponseInfoVO.success(ticketService.queryOneByOpenId(openId));
    }
}
