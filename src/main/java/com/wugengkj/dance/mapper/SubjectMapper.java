package com.wugengkj.dance.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wugengkj.dance.entity.Subject;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author leaf
 * <p>date: 2017/9/15 20:31</p>
 */
@Mapper
@Repository
public interface SubjectMapper extends BaseMapper<Subject> {


}
