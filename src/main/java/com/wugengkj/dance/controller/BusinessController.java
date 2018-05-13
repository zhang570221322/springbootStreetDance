package com.wugengkj.dance.controller;

import com.wugengkj.dance.common.vo.ResponseInfoVO;
import com.wugengkj.dance.entity.Business;
import com.wugengkj.dance.service.IBusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author leaf
 * <p>date: 2018-05-10 20:27</p>
 * <p>version: 1.0</p>
 */
@Api(value = "商户请求相关", description = "/business")
@RequestMapping("/business")
@RestController
@CrossOrigin
public class BusinessController {

    @Autowired
    private IBusinessService businessService;

    @ApiOperation("获取商户信息")
    @PostMapping("info")
    public ResponseInfoVO getBusinessInfo() {
        Business business = businessService.queryOneById(1L);
        // 不再序列化关键信息(不使用置空方式)
        return ResponseInfoVO.success(business);
    }
}
