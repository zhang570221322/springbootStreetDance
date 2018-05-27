package com.wugengkj.dance.controller;

import com.wugengkj.dance.common.vo.ErrorTemplateVO;
import com.wugengkj.dance.common.vo.ResponseInfoVO;
import com.wugengkj.dance.entity.Business;
import com.wugengkj.dance.service.IBusinessService;
import io.swagger.annotations.*;
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
@Api(description = "商户请求相关")
@RequestMapping("business")
@RestController
@CrossOrigin
public class BusinessController {

    @Autowired
    private IBusinessService businessService;

    @ApiOperation("获取商户信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功", response = Business.class),
            @ApiResponse(code = 500, message = "请求失败", response = ErrorTemplateVO.class)
    })
    @PostMapping("info")
    public ResponseInfoVO getBusinessInfo() {
        Business business = businessService.queryOneById(1L);
        // 不再序列化关键信息(不使用置空方式)
        return ResponseInfoVO.success(business);
    }
}
