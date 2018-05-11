package com.wugengkj.springboot.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wugengkj.springboot.entity.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author leaf
 * <p>date: 2017/9/15 20:31</p>
 */
@Mapper
@Repository
public interface RecordMapper extends BaseMapper<Record> {

    /**
     * 更新答题结果
     *
     * @param openId openId
     * @param subjectId 题目编号
     * @param isTrue 是否正确
     * @return 更新数量
     */
    int updateRecordResult(@Param("openId") String openId, @Param("subjectId") Long subjectId, @Param("isTrue") Integer isTrue);
}
