package com.wugengkj.springboot.controller;

import com.wugengkj.springboot.common.vo.ResponseInfoVO;
import com.wugengkj.springboot.entity.Business;
import com.wugengkj.springboot.service.IBusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "票请求相关", description = "/ticket")
@RequestMapping("/ticket")
@RestController
@CrossOrigin
public class TicketController {

    @Autowired
    private IBusinessService businessService;

    @ApiOperation("获取剩余票数")
    @PostMapping("get/nums")
    public ResponseInfoVO nums() {
        Business business = businessService.queryOneById(1L);
        return ResponseInfoVO.success(business.getSurplusTicket());
    }
}
