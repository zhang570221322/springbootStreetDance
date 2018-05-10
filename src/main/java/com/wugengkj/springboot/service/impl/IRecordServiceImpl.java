package com.wugengkj.springboot.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.wugengkj.springboot.common.enums.SubjectResultStatus;
import com.wugengkj.springboot.entity.Record;
import com.wugengkj.springboot.mapper.RecordMapper;
import com.wugengkj.springboot.service.IRecordService;
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
public class IRecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {

    @Cacheable(value = "records", key = "#p0")
    @Override
    public List<Record> queryListByOpenId(String openId) {
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
        boolean b = updateBatchById(list);
        forceUpdateCache(openId, true);
        return b;
    }

    @Override
    public boolean updateBatchIsTrue(String openId, Map<String, Integer> results) {
        List<Record> list = queryListByOpenId(openId);
        for (Record next : list) {
            Integer result = results.get(next.getSubjectId().toString());
            if (result != null && openId.equals(next.getOpenId())) {
                next.setIsTrue(result);
            }
        }
        boolean b = updateBatchById(list);
        forceUpdateCache(openId, true);
        return b;
    }

    @CachePut(value = "records", key = "#p0")
    public List<Record> forceUpdateCache(String openId, boolean b) {
        if (openId != null && !openId.isEmpty() && b) {
            return selectList(new EntityWrapper<Record>().eq("open_id", openId));
        }
        return Lists.newArrayList();
    }
}
