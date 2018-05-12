package com.wugengkj.dance.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.wugengkj.dance.entity.Star;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author leaf
 * <p>date: 2018-05-12 16:08</p>
 * <p>version: 1.0</p>
 */
@Mapper
@Repository
public interface StarMapper extends BaseMapper<Star> {
}
