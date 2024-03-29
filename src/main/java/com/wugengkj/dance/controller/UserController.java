package com.wugengkj.dance.controller;

import com.wugengkj.dance.common.dto.UserInfoDTO;
import com.wugengkj.dance.common.enums.ErrorStatus;
import com.wugengkj.dance.common.enums.SexType;
import com.wugengkj.dance.common.enums.UserStatus;
import com.wugengkj.dance.common.exception.GlobalException;
import com.wugengkj.dance.common.vo.ErrorTemplateVO;
import com.wugengkj.dance.common.vo.ResponseInfoVO;
import com.wugengkj.dance.entity.Business;
import com.wugengkj.dance.entity.Subject;
import com.wugengkj.dance.entity.Ticket;
import com.wugengkj.dance.entity.User;
import com.wugengkj.dance.service.ISubjectService;
import com.wugengkj.dance.service.ITicketService;
import com.wugengkj.dance.service.IUserService;
import com.wugengkj.dance.utils.AccessTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 用户请求
 *
 * @author leaf
 * <p>date: 2018-05-07 17:11</p>
 * <p>version: 1.0</p>
 */
@Api(description = "用户请求相关")
@RequestMapping("user")
@RestController
@CrossOrigin
@Validated
public class UserController {
    private final static String PHONE = "phone";
    private final static String QQ = "qq";

    @Autowired
    private ITicketService ticketService;

    @Autowired
    private ISubjectService subjectService;

    @Autowired
    private IUserService userService;

    @Autowired
    private AccessTokenUtil accessTokenUtil;

    @ApiOperation("获取用户状态")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功", response = Integer.class),
            @ApiResponse(code = 500, message = "请求失败", response = ErrorTemplateVO.class)
    })
    @PostMapping(value = "status")
    public ResponseInfoVO getUserStatus(@RequestParam("code") String code) {
        return ResponseInfoVO.success(userService.queryUserStatus(accessTokenUtil.getOpenId(code)));
    }

    @ApiOperation("提交用户信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功", response = Subject.class),
            @ApiResponse(code = 500, message = "请求失败", response = ErrorTemplateVO.class)
    })
    @PostMapping("post")
    public ResponseInfoVO getUserPostInfo(@RequestParam("code") String code,
                                          @Size(min = 2, message = "姓名长度不能小于2") @RequestParam("name") String name,
                                          @Range(max = 150, message = "年龄最大不超过150岁") @RequestParam("age") Integer age,
                                          @Pattern(regexp = "[男|女]", message = "无法识别该性别") @RequestParam("sex") String sex,
                                          @RequestParam("phone") String phone,
                                          @RequestParam(value = "qq", required = false) String qq,
                                          @RequestParam(value = "avatar", required = false) String avatar) {
        String openId = accessTokenUtil.getOpenId(code);
        if (userService.queryByOrderCol(PHONE, phone) != null) {
            throw new GlobalException(ErrorStatus.USER_PHONE_EXIST_ERROR);
        }
        User build = User.builder()
                .openId(openId)
                .sex(sex)
                .age(age)
                .phone(phone)
                .avatar(SexType.MALE.getName().equals(sex) ? "https://sharepoem.com/boy.png" : "https://sharepoem.com/girl.png")
                .name(name)
                .status(UserStatus.USER_NO_ANSWER.getCode())
                .ticketId(-1L)
                .build();
        if (avatar != null && !avatar.isEmpty() && !avatar.equals("")) {
            build.setAvatar(avatar);
        }
        boolean b = userService.addUser(build);
        List<Subject> randomList = subjectService.getRandomList(openId);
        return b ? ResponseInfoVO.success(randomList) : ResponseInfoVO.fail(false);
    }

    @ApiOperation("获取用户信息(包含已获得票的信息)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功", response = UserInfoDTO.class),
            @ApiResponse(code = 500, message = "请求失败", response = ErrorTemplateVO.class)
    })
    @PostMapping(value = "info")
    public ResponseInfoVO getUserInfo(@RequestParam("code") String code) {
        String openId = accessTokenUtil.getOpenId(code);
        User user = userService.queryOneByOpenId(openId);
        UserInfoDTO build = UserInfoDTO.builder().build();
        if (user != null) {
            Ticket ticket = ticketService.queryOneByOpenId(openId);
            BeanUtils.copyProperties(user, build);
            build.setTicket(ticket);
            return ResponseInfoVO.success(build);
        }
        throw new GlobalException(ErrorStatus.USER_NO_POST_INFO_ERROR);
    }
}
