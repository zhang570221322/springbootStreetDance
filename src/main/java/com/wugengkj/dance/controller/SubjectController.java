package com.wugengkj.dance.controller;

import com.wugengkj.dance.common.dto.SubjectInfoDTO;
import com.wugengkj.dance.common.vo.ResponseInfoVO;
import com.wugengkj.dance.entity.Subject;
import com.wugengkj.dance.service.ISubjectService;
import com.wugengkj.dance.utils.AccessTokenUtil;
import com.wugengkj.dance.utils.SubjectUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private SubjectUtil subjectUtil;
    @ApiOperation("获取用户题目信息")
    @PostMapping("get")
    public ResponseInfoVO getUserSubjects(@RequestParam("code") String code) {
        String openId = AccessTokenUtil.getOpenId(code);
        //得到随机对象
        List<Subject> temp = subjectService.getRandomList(openId);
        //复制对象
        List<Subject> randomList = subjectUtil.copySubjectList(temp);

        // 置空答案
        randomList.stream().forEach(subject -> subject.setAnswer(null));

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
