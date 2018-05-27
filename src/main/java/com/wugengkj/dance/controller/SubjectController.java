package com.wugengkj.dance.controller;

import com.wugengkj.dance.common.dto.SubjectInfoDTO;
import com.wugengkj.dance.common.vo.ErrorTemplateVO;
import com.wugengkj.dance.common.vo.ResponseInfoVO;
import com.wugengkj.dance.entity.Business;
import com.wugengkj.dance.entity.Subject;
import com.wugengkj.dance.service.ISubjectService;
import com.wugengkj.dance.utils.AccessTokenUtil;
import com.wugengkj.dance.utils.SubjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author leaf
 * <p>date: 2018-05-10 20:42</p>
 * <p>version: 1.0</p>
 */
@Api(description = "题目相关请求")
@RequestMapping("subject")
@RestController
@CrossOrigin
public class SubjectController {
    @Autowired
    private ISubjectService subjectService;

    @Autowired
    private AccessTokenUtil accessTokenUtil;

    @ApiOperation("获取用户题目信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功", response = Subject.class),
            @ApiResponse(code = 500, message = "请求失败", response = ErrorTemplateVO.class)
    })
    @PostMapping("get")
    public ResponseInfoVO getUserSubjects(@RequestParam("code") String code) {
        String openId = accessTokenUtil.getOpenId(code);
        // 得到随机对象
        List<Subject> randomList = subjectService.getRandomList(openId);
        return ResponseInfoVO.success(randomList);
    }

    @ApiOperation("用户提交题目")
    @ApiResponses({
            @ApiResponse(code = 200, message = "请求成功", response = Map.class),
            @ApiResponse(code = 500, message = "请求失败", response = ErrorTemplateVO.class)
    })
    @PostMapping("post")
    public ResponseInfoVO postUserResult(@RequestParam("code") String code, @RequestParam("list") String list) {
        List<SubjectInfoDTO> subjects = new ArrayList<>();
        // 分割，重新拼接参数
        String[] split = list.split(",");
        for (String aSplit : split) {
            String[] split1 = aSplit.split(":");
            subjects.add(SubjectInfoDTO.builder().id(Long.parseLong(split1[0])).result(split1[1]).build());
        }
        String openId = accessTokenUtil.getOpenId(code);
        Map<Long, String> results = new HashMap<>(11);
        subjects.stream().forEach(dto -> results.put(dto.getId(), dto.getResult()));
        return ResponseInfoVO.success(subjectService.postUserSubjectResult(openId, results));
    }
}
