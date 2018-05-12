package com.wugengkj.dance.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wugengkj.dance.common.constants.GlobalConstants;
import com.wugengkj.dance.common.enums.ErrorStatus;
import com.wugengkj.dance.common.enums.SubjectResultStatus;
import com.wugengkj.dance.common.enums.SubjectStatus;
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
import com.wugengkj.dance.utils.SubjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private ISubjectService subjectService;

    @Autowired
    private SubjectUtil subjectUtil;

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
    //增加事务
    @Transactional
    @Override
    public List<Subject> getRandomList(String openId) {
        List<Subject> subjectList = subjectService.queryList(-1);
        List<Subject> list = new ArrayList<>();
        int i = userService.queryUserStatus(openId);
        if (i == UserStatus.USER_ANSWERING.getCode() || i == UserStatus.USER_ANSWERED.getCode()) {
            List<Record> records = recordService.queryListByOpenId(openId);
            log.info("检测用户" + openId + "状态为" + UserStatus.USER_ANSWERING.getName() + "，" +
                    "获取已保存题目编号为" + records.stream().map(Record::getSubjectId).collect(Collectors.toList()).toString());
            for (Record record : records) {
                list.add(subjectList.get((int) (record.getSubjectId() - 1)));
            }
            //此时也是内存中的题目
            return list;
        } else if (i == UserStatus.USER_NO_ANSWER.getCode()) {
            // 随机获取题目
            list = subjectUtil.randomSubject();

            log.info("检测用户" + openId + "状态为" + UserStatus.USER_ANSWERING.getName() + "，" +
                    "随机生成题目编号为" + list.toString());

            // 修改用户状态为答题中
            User user = userService.queryOneByOpenId(openId);
            user.setStatus(UserStatus.USER_ANSWERING.getCode());
            userService.updateUserStatus(user);

            log.info("修改用户" + openId + "状态为" + UserStatus.USER_ANSWERING.getName());

            // 添加答题记录
            List<Long> subjects = list.stream().map(Subject::getId).collect(Collectors.toList());
            recordService.addBatchByOpenIdAndSubjects(openId, subjects);
            return list;
        } else {
            return null;
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
        List<Subject> cacheSubjects = subjectService.queryList(-1);
        int i = 0, j = 0;
        for (Map.Entry<Long, String> next : userResults.entrySet()) {
            Subject subject = cacheSubjects.get((int) (next.getKey() - 1));
            if (subject != null) {
                if (subject.getAnswer().equalsIgnoreCase(next.getValue())) {
                    results.put(subject.getId().toString(), SubjectResultStatus.ANSWER_SUCCESS.getCode());
                    j++;
                } else {
                    results.put(subject.getId().toString(), SubjectResultStatus.ANSWER_FAIL.getCode());
                    i++;
                }
            }
        }
        results.put("SUCCESS_NUMS", j);
        results.put("ERROR_NUMS", i);
        return results;
    }

    @Transactional
    @Override
    public Map<String, Object> postUserSubjectResult(String openId, Map<Long, String> results) {
        Map<String, Object> ticketMap = new HashMap<>();
        // 获取用户信息(用于用户状态判断)
        User user = userService.queryOneByOpenId(openId);
        if (UserStatus.USER_ANSWERED.getCode().equals(user.getStatus())) {
            log.error("用户" + openId +"已答题!");
            Ticket ticket = ticketService.queryOneByOpenId(openId);
            ticketMap.put("ticket", ticket);
            return ticketMap;
        }
        // 结果比较
        log.info("比较用户" + openId + "提交结果，比对中...");
        Map<String, Integer> resultMap = validSubjectResult(results);
        ticketMap.put("results", resultMap);
        // 更新答题记录
        log.info("开始更新用户" + openId + "答题结果，正在更新...");
        boolean b = recordService.updateBatchIsTrue(openId, resultMap);
        if (b && UserStatus.USER_ANSWERING.getCode().equals(user.getStatus())) {
            log.info("更新用户答题成功!");
            Integer successNums = resultMap.get("SUCCESS_NUMS");
            int i = subjectUtil.ticketId(successNums);

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
                Ticket ticket = ticketService.queryOneByOpenId(openId);
                ticketMap.put("ticket", ticket);
                return ticketMap;
            }
            throw new GlobalException(ErrorStatus.GLOBAL_ERROR);
        }
        log.error("更新用户" + openId +"答题记录失败!");
        return null;
    }

    @Override
    public boolean addBatchSubject(List<Subject> list) {
        return insertBatch(list);
    }

    /**
     * 获取简单的题目
     *
     * @return
     */
    @Override
    public ArrayList<Subject> queryEasyList() {
        return (ArrayList<Subject>) subjectService.queryList( -1)
                .stream()
                .filter(subject ->
                        subject.getType().equals(SubjectStatus.EASY.getCode())
                )
                .collect(Collectors.toList());

    }

    /**
     * 获取中等题目
     *
     * @return
     */
    @Override
    public ArrayList<Subject> queryMediumList() {
        return (ArrayList<Subject>) subjectService.queryList(-1)
                .stream()
                .filter(subject ->
                        subject.getType().equals(SubjectStatus.MEDIUM.getCode())
                )
                .collect(Collectors.toList());
    }

    /**
     * 获取难题
     *
     * @return
     */
    @Override
    public ArrayList<Subject> queryHardList() {
        return (ArrayList<Subject>) subjectService.queryList(-1)
                .stream()
                .filter(subject ->
                        subject.getType().equals(SubjectStatus.HARD.getCode())
                )
                .collect(Collectors.toList());
    }

    @CacheEvict(allEntries = true)
    @Override
    public void removeCache() {
        log.info("清理subjects缓存信息!");
    }
}
