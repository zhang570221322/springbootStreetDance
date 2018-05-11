package com.wugengkj.springboot.controller;

import com.wugengkj.springboot.common.enums.ErrorStatus;
import com.wugengkj.springboot.common.enums.UserStatus;
import com.wugengkj.springboot.common.exception.GlobalException;
import com.wugengkj.springboot.common.vo.ResponseInfoVO;
import com.wugengkj.springboot.entity.Subject;
import com.wugengkj.springboot.entity.User;
import com.wugengkj.springboot.service.ISubjectService;
import com.wugengkj.springboot.service.ITicketService;
import com.wugengkj.springboot.service.IUserService;
import com.wugengkj.springboot.utils.AccessTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private ISubjectService subjectService;

    @Autowired
    private IUserService userService;

    @ApiOperation("获取用户状态")
    @PostMapping(value = "status")
    public ResponseInfoVO getUserStatus(@RequestParam("code") String code) {
        return ResponseInfoVO.success(userService.queryUserStatus(AccessTokenUtil.getOpenId(code)));
    }

    @ApiOperation("提交用户信息")
    @PostMapping("post")
    public ResponseInfoVO getUserPostInfo(@RequestParam("code") String code,
                                          @RequestParam("name") String name,
                                          @RequestParam("age") Integer age,
                                          @RequestParam("sex") String sex,
                                          @RequestParam("phone") String phone,
                                          @RequestParam("qq") String qq) {
        String openId = AccessTokenUtil.getOpenId(code);
        if (userService.queryByOrderCol("phone", phone) != null) {
            throw new GlobalException(ErrorStatus.USER_PHONE_EXIST_ERROR);
        }
        if (userService.queryByOrderCol("qq", qq) != null) {
            throw new GlobalException(ErrorStatus.USER_QQ_EXIST_ERROR);
        }
        User build = User.builder()
                .openId(openId)
                .sex(sex)
                .age(age)
                .phone(phone)
                .qq(qq)
                .name(name)
                .status(UserStatus.USER_NO_ANSWER.getCode())
                .build();
        boolean b = userService.addUser(build);
        List<Subject> randomList = subjectService.getRandomList(openId);
        // 置空答案
        for(Subject subject : randomList) {
            subject.setAnswer(null);
        }
        return b ? ResponseInfoVO.success(randomList) : ResponseInfoVO.fail(false);
    }

    @ApiOperation("获取用户得票信息")
    @PostMapping(value = "ticket")
    public ResponseInfoVO getUserTicket(@RequestParam("code") String code) {
        String openId = AccessTokenUtil.getOpenId(code);
        return ResponseInfoVO.success(ticketService.queryOneByOpenId(openId));
    }
}
