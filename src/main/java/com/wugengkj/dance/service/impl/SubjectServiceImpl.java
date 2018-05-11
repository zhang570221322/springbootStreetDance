package com.wugengkj.dance.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wugengkj.dance.common.constants.GlobalConstants;
import com.wugengkj.dance.common.enums.ErrorStatus;
import com.wugengkj.dance.common.enums.SubjectResultStatus;
import com.wugengkj.dance.common.enums.UserStatus;
import com.wugengkj.dance.common.exception.GlobalException;
import com.wugengkj.dance.entity.Record;
import com.wugengkj.dance.entity.Subject;
import com.wugengkj.dance.entity.Ticket;
import com.wugengkj.dance.entity.User;
import com.wugengkj.dance.mapper.SubjectMapper;
import com.wugengkj.dance.service.IRecordService;
import com.wugengkj.dance.service.ISubjectService;
import com.wugengkj.dance.service.ITicketService;
import com.wugengkj.dance.service.IUserService;
import com.wugengkj.dance.utils.AccessTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
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
@CacheConfig(cacheNames = "subjects")
@Slf4j
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements ISubjectService {

    @Autowired
    private IRecordService recordService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ITicketService ticketService;

    @Cacheable(key = "#p0")
    @Override
    public List<Subject> queryList(int key) {
        log.info("添加subjects缓存!");
        return super.selectList(new EntityWrapper<>());
    }

    @Override
    public List<Subject> getRandomList(String openId) {
        List<Subject> subjectList = this.queryList(-1);
        List<Subject> list = new ArrayList<>();
        int i = userService.queryUserStatus(openId);
        if (i == UserStatus.USER_ANSWERING.getCode()) {
            List<Record> records = recordService.queryListByOpenId(openId);
            log.info("检测用户" + openId + "状态为" + UserStatus.USER_ANSWERING.getName() + "，" +
                    "获取已保存题目编号为" + records.stream().map(Record::getSubjectId).collect(Collectors.toList()).toString());
            for (Record record : records) {
                list.add(subjectList.get((int) (record.getSubjectId() - 1)));
            }
            return list;
        } else {
            // 随机获取题目
            List<Integer> list1 = AccessTokenUtil.randomSubject();
            for (Integer integer : list1) {
                list.add(subjectList.get(integer));
            }
            log.info("检测用户" + openId + "状态为" + UserStatus.USER_ANSWERING.getName() + "，" +
                    "随机生成题目编号为" + list1.toString());

            // 修改用户状态为答题中
            User user = userService.queryOneByOpenId(openId);
            user.setStatus(UserStatus.USER_ANSWERING.getCode());
            userService.updateUserStatus(user);

            log.info("修改用户" + openId + "状态为" + UserStatus.USER_ANSWERING.getName());

            // 添加答题记录
            List<Long> subjects = list.stream().map(Subject::getId).collect(Collectors.toList());
            recordService.addBatchByOpenIdAndSubjects(openId, subjects);
            return list;
        }
    }

    /**
     * 验证用户提交答案
     *
     * @param userResults 用户题目结果map
     * @return 用户最终结果
     */
    private Map<String, Integer> validSubjectResult(Map<Long, String> userResults) {
        Map<String, Integer> results = new HashMap<>();
        List<Subject> cacheSubjects = queryList(-1);
        int i = 0, j = 0;
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
        log.info("比较用户" + openId + "提交结果，比对中...");
        Map<String, Integer> resultMap = validSubjectResult(results);
        // 更新答题记录
        log.info("开始更新用户" + openId + "答题结果，正在更新...");
        boolean b = recordService.updateBatchIsTrue(openId, resultMap);
        // 计算用户票并添加
        User user = userService.queryOneByOpenId(openId);
        if (b) {
            log.info("更新用户答题成功!");
            Integer successNums = resultMap.get("SUCCESS_NUMS");
            int i = AccessTokenUtil.ticketId(successNums, GlobalConstants.PRE_USER_TICKET_NUM, GlobalConstants.TICKET_TYPE_NUM);

            // 修改用户答题状态为答题完成
            log.info("更新用户" + openId + "答题状态为" + UserStatus.USER_ANSWERED.getName());
            user.setTicketId((long) i);
            user.setPostTime(new Date());
            user.setStatus(UserStatus.USER_ANSWERED.getCode());
            userService.updateUserStatus(user);

            // 票数进行相应减少
            boolean b1 = ticketService.reduceOneTicket((long) i, 1L);
            if (b1) {
                // 获取票信息
                log.info("生成用户所得票信息成功!");
                return ticketService.queryOneByOpenId(openId);
            }
            throw new GlobalException(ErrorStatus.GLOBAL_ERROR);
        }
        log.error("更新用户答题失败!");
        return null;
    }

    @Override
    public boolean addBatchSubject(List<Subject> list) {
        return insertBatch(list);
    }

    @CacheEvict(allEntries = true)
    @Override
    public void removeCache() {
        log.info("清理subjects缓存信息!");
    }
}
