package com.wugengkj.dance.controller;

import com.wugengkj.dance.common.vo.ResponseInfoVO;
import com.wugengkj.dance.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "缓存请求相关", description = "/cache")
@RequestMapping("/cache")
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

    @ApiOperation("清理所有缓存信息")
    @PostMapping("clean")
    public ResponseInfoVO clean() {
        businessService.removeCache();
        userService.removeCache();
        recordService.removeCache();
        subjectService.removeCache();
        ticketService.removeCache();
        return ResponseInfoVO.success(true);
    }
}
