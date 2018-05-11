package com.wugengkj.springboot.controller;

import com.wugengkj.springboot.common.dto.SubjectInfoDTO;
import com.wugengkj.springboot.common.vo.ResponseInfoVO;
import com.wugengkj.springboot.entity.Subject;
import com.wugengkj.springboot.service.ISubjectService;
import com.wugengkj.springboot.utils.AccessTokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(value = "题目相关请求", description = "/subject")
@RequestMapping("/subject")
@RestController
@CrossOrigin
public class SubjectController {
    @Autowired
    private ISubjectService subjectService;

    @ApiOperation("获取用户题目信息")
    @PostMapping("get")
    public ResponseInfoVO getUserSubjects(@RequestParam("code") String code) {
        String openId = AccessTokenUtil.getOpenId(code);
        List<Subject> randomList = subjectService.getRandomList(openId);

        // 置空答案
        for(Subject subject : randomList) {
            subject.setAnswer(null);
        }

        return ResponseInfoVO.success(randomList);
    }

    @ApiOperation("用户提交题目")
    @PostMapping("post")
    public ResponseInfoVO postUserResult(@RequestHeader String code, @RequestBody List<SubjectInfoDTO> subjects) {
        String openId = AccessTokenUtil.getOpenId(code);
        Map<Long, String> results = new HashMap<>();
        subjects.stream().forEach(dto -> results.put(dto.getId(), dto.getResult()));
        return ResponseInfoVO.success(subjectService.postUserSubjectResult(openId, results));
    }
}
