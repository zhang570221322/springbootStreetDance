package com.wugengkj.springboot.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wugengkj.springboot.entity.Record;
import com.wugengkj.springboot.mapper.RecordMapper;
import com.wugengkj.springboot.service.IRecordService;
import org.springframework.stereotype.Service;

/**
 * @author leaf
 * <p>date: 2018-05-10 14:31</p>
 * <p>version: 1.0</p>
 */
@Service
public class IRecordServiceImpl extends ServiceImpl<RecordMapper, Record> implements IRecordService {

}
