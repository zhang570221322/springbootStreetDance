package com.wugengkj.dance.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 票
 *
 * @author leaf
 * <p>date: 2018-05-08 11:13</p>
 * <p>version: 1.0</p>
 */
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Ticket {
    @TableId
    private Long id;
    private String name;
    private String content;
    private String detail;
    @JsonIgnore
    private Integer total;
    @JsonIgnore
    private Integer currentNum;
    @JsonIgnore
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date validTime;
}
