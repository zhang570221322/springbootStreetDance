package com.wugengkj.springboot.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wugengkj.springboot.common.constants.GlobalConstants;
import com.wugengkj.springboot.common.enums.SubjectResultStatus;
import com.wugengkj.springboot.common.enums.UserStatus;
import com.wugengkj.springboot.entity.Record;
import com.wugengkj.springboot.entity.Subject;
import com.wugengkj.springboot.entity.Ticket;
import com.wugengkj.springboot.entity.User;
import com.wugengkj.springboot.mapper.SubjectMapper;
import com.wugengkj.springboot.service.IRecordService;
import com.wugengkj.springboot.service.ISubjectService;

import com.wugengkj.springboot.service.ITicketService;
import com.wugengkj.springboot.service.IUserService;
import com.wugengkj.springboot.utils.AccessTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zwl
 * @date 2018/5/10
 * May god bless me
 */
@Service
@Slf4j
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements ISubjectService {

    @Autowired
    private IRecordService recordService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ITicketService ticketService;

    @Cacheable(value = "queryList", key = "#p0")
    @Override
    public List<Subject> queryList(int key) {
        return super.selectList(new EntityWrapper<>());
    }

    @Override
    public List<Subject> getRandomList(String openId) {
        List<Subject> subjectList = this.queryList(-1);
        List<Subject> list = new ArrayList<>();
        int i = userService.queryUserStatus(openId);
        if (i == UserStatus.USER_ANSWERING.getCode()) {
            List<Record> records = recordService.queryListByOpenId(openId);
            for (Record record : records) {
                list.add(subjectList.get((int) (record.getSubjectId() - 1)));
            }
            return list;
        } else {
            // 随机获取题目
            List<Integer> list1 = AccessTokenUtil.randomSubject();
            for (Integer integer : list1) {
                list.add(subjectList.get(integer - 1));
            }

            // 修改用户状态为答题中
            User user = userService.queryOneByOpenId(openId);
            user.setStatus(UserStatus.USER_ANSWERING.getCode());
            userService.updateUserStatus(user);

            // 添加答题记录
            List<Long> subjects = list.stream().map(Subject::getId).collect(Collectors.toList());
            recordService.addBatchByOpenIdAndSubjects(openId, subjects);
            return list;
        }
    }

    private Map<String, Integer> validSubjectResult(Map<Long, String> userResults) {
        Map<String, Integer> results = new HashMap<>();
        List<Subject> cacheSubjects = queryList(-1);
        int i = 1, j = 1;
        for (Map.Entry<Long, String> next : userResults.entrySet()) {
            Subject subject = cacheSubjects.get((int) (next.getKey() - 1));
            if (subject.getAnswer().equalsIgnoreCase(next.getValue())) {
                results.put(subject.getId().toString(), SubjectResultStatus.ANSWER_SUCCESS.getCode());
                j++;
            } else {
                results.put(subject.getId().toString(), SubjectResultStatus.ANSWER_FAIL.getCode());
                i++;
            }
        }
        results.put("SUCCESS_NUMS", j);
        results.put("ERROR_NUMS", i);
        return results;
    }

    @Override
    public Ticket postUserSubjectResult(String openId, Map<Long, String> results) {
        // 结果比较
        Map<String, Integer> resultMap = validSubjectResult(results);
        // 更新答题记录
        boolean b = recordService.updateBatchIsTrue(openId, resultMap);
        // 计算用户票并添加
        User user = userService.queryOneByOpenId(openId);
        if (b) {
            Integer successNums = resultMap.get("SUCCESS_NUMS");
            int i = AccessTokenUtil.ticketId(successNums, GlobalConstants.PRE_USER_TICKET_NUM, GlobalConstants.TICKET_TYPE_NUM);

            // 修改用户答题状态为答题完成
            user.setTicketId((long) i);
            user.setStatus(UserStatus.USER_ANSWERED.getCode());
            userService.updateUserStatus(user);

            // 获取票信息
            return ticketService.queryOneByOpenId(openId);
        }

        return null;
    }
}
