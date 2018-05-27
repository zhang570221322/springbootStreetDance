package com.wugengkj.dance.controller;

import com.wugengkj.dance.common.vo.ErrorTemplateVO;
import com.wugengkj.dance.common.vo.ResponseInfoVO;
import com.wugengkj.dance.entity.Business;
import com.wugengkj.dance.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author leaf
 * <p>date: 2018-05-11 14:54</p>
 * <p>version: 1.0</p>
 */
@Api(description = "缓存请求相关")
@RequestMapping("cache")
@RestController
@CrossOrigin
public class CacheController {
    @Autowired
    private IBusinessService businessService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IRecordService recordService;
    @Autowired
    private ISubjectService subjectService;
    @Autowired
    private ITicketService ticketService;
    @Autowired
    private IStarService starService;

    @ApiOperation("清理所有缓存信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功", response = Boolean.class),
            @ApiResponse(code = 500, message = "请求失败", response = ErrorTemplateVO.class)
    })
    @PostMapping("clean")
    public ResponseInfoVO clean() {
        businessService.removeCache();
        userService.removeCache();
        recordService.removeCache();
        subjectService.removeCache();
        ticketService.removeCache();
        starService.removeCache();
        return ResponseInfoVO.success(true);
    }
}
