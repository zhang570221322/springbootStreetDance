package com.wugengkj.springboot.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 答题记录
 *
 * @author leaf
 * <p>date: 2018-05-08 11:11</p>
 * <p>version: 1.0</p>
 */
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Record {
    @TableId
    private String openId;
    @TableId
    private Long subjectId;
    private Integer status;
    private Integer isTrue;
    private Date createTime;
    private Date updateTime;
}
