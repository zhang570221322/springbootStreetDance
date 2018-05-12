package com.wugengkj.dance.controller;

import com.wugengkj.dance.common.vo.ResponseInfoVO;
import com.wugengkj.dance.service.IStarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author leaf
 * <p>date: 2018-05-12 16:12</p>
 * <p>version: 1.0</p>
 */
@Api(value = "明星请求相关", description = "/star")
@RequestMapping("/star")
@RestController
@CrossOrigin
public class StarController {

    @Autowired
    private IStarService starService;

    @ApiOperation("获取全部明星信息")
    @PostMapping("/all")
    public ResponseInfoVO all() {
        return ResponseInfoVO.success(starService.getAll(-1));
    }
}
