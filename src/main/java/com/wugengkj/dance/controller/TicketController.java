package com.wugengkj.dance.controller;

import com.wugengkj.dance.common.vo.ErrorTemplateVO;
import com.wugengkj.dance.common.vo.ResponseInfoVO;
import com.wugengkj.dance.entity.Business;
import com.wugengkj.dance.service.IBusinessService;
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
 * <p>date: 2018-05-10 20:33</p>
 * <p>version: 1.0</p>
 */
@Api(description = "票请求相关")
@RequestMapping("ticket")
@RestController
@CrossOrigin
public class TicketController {

    @Autowired
    private IBusinessService businessService;

    @ApiOperation("获取剩余票数")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功", response = Integer.class),
            @ApiResponse(code = 500, message = "请求失败", response = ErrorTemplateVO.class)
    })
    @PostMapping("get/nums")
    public ResponseInfoVO nums() {
        Business business = businessService.queryOneById(1L);
        return ResponseInfoVO.success(business.getSurplusTicket());
    }
}
