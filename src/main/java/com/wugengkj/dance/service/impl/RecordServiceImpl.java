package com.wugengkj.dance.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.wugengkj.dance.common.enums.SubjectResultStatus;
import com.wugengkj.dance.entity.Record;
import com.wugengkj.dance.mapper.RecordMapper;
import com.wugengkj.dance.service.IRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author leaf
 * <p>date: 2018-05-10 14:31</p>
 * <p>version: 1.0</p>
 */
@Service
@CacheConfig(cacheNames = "records")
@Slf4j
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {

    @Cacheable(key = "#p0")
    @Override
    public List<Record> queryListByOpenId(String openId) {
        log.info("添加records缓存!");
        return selectList(new EntityWrapper<Record>().eq("open_id", openId));
    }

    @Override
    public boolean addBatchByOpenIdAndSubjects(String openId, List<Long> subjects) {
        List<Record> list = new ArrayList<>();
        for (Long subject : subjects) {
            list.add(Record.builder()
                    .openId(openId)
                    .isTrue(SubjectResultStatus.NO_ANSWER.getCode())
                    .subjectId(subject).build());
        }
        log.info("批量插入用户" + openId + "答题记录，题目编号:" + subjects.toString());
        boolean b = insertBatch(list);
        forceUpdateCache(openId, true);
        return b;
    }

    @Override
    public boolean updateBatchIsTrue(String openId, Map<String, Integer> results) {
        List<Record> list = queryListByOpenId(openId);
        if (list == null || list.size() == 0) {
            return false;
        }
        int i = 1;
        for (Record next : list) {
            Integer result = results.get(next.getSubjectId().toString());
            if (result != null && openId.equals(next.getOpenId()) && next.getIsTrue().equals(SubjectResultStatus.NO_ANSWER.getCode())) {
                next.setIsTrue(result);
                i = baseMapper.updateRecordResult(openId, next.getSubjectId(), result);
                log.info("更新用户" + openId + "答题记录，题目编号为" + next.getSubjectId() + "，更新结果为" + result);
            }
        }
        forceUpdateCache(openId, true);
        return i == 1;
    }

    @CachePut(key = "#p0")
    public List<Record> forceUpdateCache(String openId, boolean b) {
        if (openId != null && !openId.isEmpty() && b) {
            log.info("强制更新records缓存!");
            return selectList(new EntityWrapper<Record>().eq("open_id", openId));
        }
        return Lists.newArrayList();
    }

    @CacheEvict(allEntries = true)
    @Override
    public void removeCache() {
        log.info("清理records缓存信息!");
    }
}
