package com.wugengkj.springboot.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 题目
 *
 * @author leaf
 * <p>date: 2018-05-08 11:10</p>
 * <p>version: 1.0</p>
 */
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Subject {
    @TableId(type = IdType.INPUT)
    private Long id;
    private String title;
    private String content;
    private String answer;
    private Integer type;
}
