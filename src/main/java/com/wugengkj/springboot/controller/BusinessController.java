package com.wugengkj.springboot.controller;

import com.wugengkj.springboot.common.vo.ResponseInfoVO;
import com.wugengkj.springboot.entity.Business;
import com.wugengkj.springboot.service.IBusinessService;
import com.wugengkj.springboot.utils.AccessTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResponseInfoVO info(@RequestParam("code") String code) {
        AccessTokenUtil.getOpenId(code);
        Business business = businessService.queryOneById(1L);
        // 置空关键信息
        business.setAppid(null);
        business.setSurplusTicket(null);
        business.setTotalTicket(null);
        return ResponseInfoVO.success(business);
    }
}
